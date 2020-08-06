package com.leonpahole.workoutapp.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.leonpahole.workoutapp.controller.WorkoutController;
import com.leonpahole.workoutapp.dto.ExercisePerformedDto;
import com.leonpahole.workoutapp.dto.ExercisePerformedSetsDto;
import com.leonpahole.workoutapp.dto.WorkoutDto;
import com.leonpahole.workoutapp.dto.WorkoutTemplateDto;
import com.leonpahole.workoutapp.errors.ApplicationException;
import com.leonpahole.workoutapp.model.*;
import com.leonpahole.workoutapp.repository.ExercisePerformedRepository;
import com.leonpahole.workoutapp.repository.ExercisePerformedSetRepository;
import com.leonpahole.workoutapp.repository.ExerciseRepository;
import com.leonpahole.workoutapp.repository.WorkoutRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final ExercisePerformedRepository exercisePerformedRepository;
    private final ExercisePerformedSetRepository exercisePerformedSetRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserService userService;
    private final WorkoutTemplateService workoutTemplateService;

    @Transactional
    public WorkoutController.CreateWorkoutResponse createWorkout(WorkoutDto workout) {
        Workout createdWorkout = workoutDtoToWorkout(workout);

        HashMap<Long, Exercise> allExercises = new HashMap<>();

        for (ExercisePerformed exercisePerformed : createdWorkout.getExercisesPerformed()) {
            if (!allExercises.containsKey(exercisePerformed.getExerciseId())) {
                Exercise currentExercise = exerciseRepository.findById(exercisePerformed.getExerciseId())
                        .orElseThrow(() -> new ApplicationException(
                                "Exercise with id " + exercisePerformed.getExerciseId() + " not found"));
                allExercises.put(exercisePerformed.getExerciseId(), currentExercise);
            }
        }

        User currentUser = userService.getCurrentUser();
        createdWorkout.setUser(currentUser);
        workoutRepository.save(createdWorkout);

        for (ExercisePerformed exercisePerformed : createdWorkout.getExercisesPerformed()) {
            exercisePerformed.setWorkout(createdWorkout);
            exercisePerformed.setExercise(allExercises.get(exercisePerformed.getExerciseId()));
            exercisePerformedRepository.save(exercisePerformed);

            for (ExercisePerformedSet exercisePerformedSet : exercisePerformed.getExercisePerformedSets()) {
                exercisePerformedSet.setExercisePerformed(exercisePerformed);
                exercisePerformedSetRepository.save(exercisePerformedSet);
            }
        }

        Long templateId = null;
        if (workout.getSaveAsTemplate()) {
            WorkoutTemplateDto template = workoutTemplateService.workoutDtoToWorkoutTemplateDto(workout);
            templateId = workoutTemplateService.createWorkoutTemplate(template);
        }

        return new WorkoutController.CreateWorkoutResponse(createdWorkout.getId(), templateId);
    }

    private Workout workoutDtoToWorkout(WorkoutDto workoutDto) {
        Workout workout = new Workout();
        workout.setName(workoutDto.getName());
        workout.setComment(workoutDto.getComment());
        workout.setStartDate(workoutDto.getStartDate().toInstant());
        workout.setStartTime(workoutDto.getStartTime() == null ? null : workoutDto.getStartTime().toInstant());
        workout.setEndTime(workoutDto.getEndTime() == null ? null : workoutDto.getEndTime().toInstant());
        workout.setCreatedAt(Instant.now());

        List<ExercisePerformed> exercisesPerformed = new ArrayList<>();

        int exerciseOrder = 1;
        for (ExercisePerformedDto exercisePerfomedDto : workoutDto.getExercisesPerformed()) {
            ExercisePerformed exercisePerformed = new ExercisePerformed();
            exercisePerformed.setOrder(exerciseOrder++);
            exercisePerformed.setExerciseId(exercisePerfomedDto.getExerciseId());
            exercisePerformed.setRest(exercisePerfomedDto.getRest());

            List<ExercisePerformedSet> exercisePerformedSets = new ArrayList<>();

            int setOrder = 1;
            for (ExercisePerformedSetsDto exercisePerfomedSetDto : exercisePerfomedDto.getSets()) {
                ExercisePerformedSet set = new ExercisePerformedSet();
                set.setOrder(setOrder++);
                set.setRepetitions(exercisePerfomedSetDto.getRepetitions());
                set.setTime(exercisePerfomedSetDto.getTime());
                set.setWeight(exercisePerfomedSetDto.getWeight());

                exercisePerformedSets.add(set);
            }

            exercisePerformed.setExercisePerformedSets(exercisePerformedSets);

            exercisesPerformed.add(exercisePerformed);
        }

        workout.setExercisesPerformed(exercisesPerformed);
        return workout;
    }

    @Transactional
    public WorkoutDto getWorkout(Long id) {
        Workout workout = workoutRepository.findByIdAndUserId(id, userService.getCurrentUser().getId())
                .orElseThrow(() -> new ApplicationException("Workout with id " + id + " not found"));
        return workoutToWorkoutDto(workout);
    }

    @Transactional
    public List<WorkoutDto> getWorkouts() {
        List<Workout> workouts = workoutRepository.findAllByUserIdOrderByCreatedAtDesc(userService.getCurrentUser().getId());
        return workouts.stream().map(this::workoutToWorkoutDto).collect(Collectors.toList());
    }

    private WorkoutDto workoutToWorkoutDto(Workout workout) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setName(workout.getName());
        workoutDto.setComment(workout.getComment());
        workoutDto.setCreatedAt(Date.from(workout.getCreatedAt()));
        workoutDto.setStartDate(Date.from(workout.getStartDate()));
        workoutDto.setStartTime(workout.getStartTime() == null ? null : Date.from(workout.getStartTime()));
        workoutDto.setEndTime(workout.getEndTime() == null ? null : Date.from(workout.getEndTime()));

        List<ExercisePerformedDto> exercisesPerformedDto = new ArrayList<>();

        for (ExercisePerformed exercisePerformed : workout.getExercisesPerformed()) {

            ExercisePerformedDto exercisePerformedDto = new ExercisePerformedDto();
            exercisePerformedDto.setExerciseId(exercisePerformed.getExercise().getId());
            exercisePerformedDto.setRest(exercisePerformed.getRest());

            List<ExercisePerformedSetsDto> exercisePerformedSetsDto = new ArrayList<>();

            for (ExercisePerformedSet exercisePerformedSet : exercisePerformed.getExercisePerformedSets()) {
                ExercisePerformedSetsDto exercisePerformedSetDto = new ExercisePerformedSetsDto();

                exercisePerformedSetDto.setRepetitions(exercisePerformedSet.getRepetitions());
                exercisePerformedSetDto.setTime(exercisePerformedSet.getTime());
                exercisePerformedSetDto.setWeight(exercisePerformedSet.getWeight());

                exercisePerformedSetsDto.add(exercisePerformedSetDto);
            }

            exercisePerformedDto.setSets(exercisePerformedSetsDto);

            exercisesPerformedDto.add(exercisePerformedDto);
        }

        workoutDto.setExercisesPerformed(exercisesPerformedDto);

        return workoutDto;
    }

    @Transactional
    @Modifying
    public WorkoutController.CreateWorkoutResponse updateWorkout(Long id, WorkoutDto updatedWorkout) {

        Workout workout = workoutRepository.findByIdAndUserId(id, userService.getCurrentUser().getId())
                .orElseThrow(() -> new ApplicationException("Workout template with id " + id + " not found"));

        HashMap<Long, Exercise> allExercises = new HashMap<>();

        for (ExercisePerformedDto exercisePerformed : updatedWorkout.getExercisesPerformed()) {
            if (!allExercises.containsKey(exercisePerformed.getExerciseId())) {
                Exercise currentExercise = exerciseRepository.findById(exercisePerformed.getExerciseId())
                        .orElseThrow(() -> new ApplicationException(
                                "Exercise with id " + exercisePerformed.getExerciseId() + " not found"));
                allExercises.put(exercisePerformed.getExerciseId(), currentExercise);
            }
        }

        workout.setName(updatedWorkout.getName());
        workout.setComment(updatedWorkout.getComment());
        workout.setStartTime(updatedWorkout.getStartTime().toInstant());
        workout.setEndTime(updatedWorkout.getEndTime().toInstant());
        workout.setStartDate(updatedWorkout.getStartDate().toInstant());
        workoutRepository.save(workout);

        Workout workoutToUpdate = workoutDtoToWorkout(updatedWorkout);

        List<Long> exercisePerformedIds = exercisePerformedRepository.findAllByWorkoutId(id).stream()
                .map(ExercisePerformed::getId).collect(Collectors.toList());

        exercisePerformedSetRepository.deleteAllByExercisePerformedIds(exercisePerformedIds);
        exercisePerformedRepository.deleteAllByWorkoutId(id);

        for (ExercisePerformed exercisePerformed : workoutToUpdate.getExercisesPerformed()) {
            exercisePerformed.setWorkout(workout);
            exercisePerformed.setExercise(allExercises.get(exercisePerformed.getExerciseId()));
            exercisePerformedRepository.save(exercisePerformed);

            for (ExercisePerformedSet exercisePerformedSet : exercisePerformed.getExercisePerformedSets()) {
                exercisePerformedSet.setExercisePerformed(exercisePerformed);
                exercisePerformedSetRepository.save(exercisePerformedSet);
            }
        }

        Long templateId = null;
        if (updatedWorkout.getSaveAsTemplate()) {
            WorkoutTemplateDto template = workoutTemplateService.workoutDtoToWorkoutTemplateDto(updatedWorkout);
            templateId = workoutTemplateService.createWorkoutTemplate(template);
        }

        return new WorkoutController.CreateWorkoutResponse(workout.getId(), templateId);
    }

    @Transactional
    @Modifying
    public WorkoutController.CreateWorkoutResponse deleteWorkout(Long id) {
        List<Long> exercisePerformedIds = exercisePerformedRepository.findAllByWorkoutId(id).stream()
                .map(ExercisePerformed::getId).collect(Collectors.toList());

        exercisePerformedSetRepository.deleteAllByExercisePerformedIds(exercisePerformedIds);
        exercisePerformedRepository.deleteAllByWorkoutId(id);
        workoutRepository.deleteById(id);
        return new WorkoutController.CreateWorkoutResponse(id, null);
    }
}