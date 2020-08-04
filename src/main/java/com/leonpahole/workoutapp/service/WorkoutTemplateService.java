package com.leonpahole.workoutapp.service;

import com.leonpahole.workoutapp.dto.*;
import com.leonpahole.workoutapp.errors.ApplicationException;
import com.leonpahole.workoutapp.model.*;
import com.leonpahole.workoutapp.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkoutTemplateService {

    private final WorkoutTemplateRepository workoutTemplateRepository;
    private final WorkoutTemplateExerciseRepository workoutTemplateExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserService userService;

    @Transactional
    public Long createWorkoutTemplate(WorkoutTemplateDto workoutTemplate) {
        WorkoutTemplate createdWorkoutTemplate = workoutTemplateDtoToWorkoutTemplate(workoutTemplate);
        User currentUser = userService.getCurrentUser();
        createdWorkoutTemplate.setUser(currentUser);
        workoutTemplateRepository.save(createdWorkoutTemplate);

        HashMap<Long, Exercise> allExercises = new HashMap<>();

        for (WorkoutTemplateExercise workoutTemplateExercise : createdWorkoutTemplate.getTemplateExercises()) {
            if (!allExercises.containsKey(workoutTemplateExercise.getExerciseId())) {
                Exercise currentExercise = exerciseRepository.findById(workoutTemplateExercise.getExerciseId())
                        .orElseThrow(() -> new ApplicationException(
                                "Exercise with id " + workoutTemplateExercise.getExerciseId() + " not found"));
                allExercises.put(workoutTemplateExercise.getExerciseId(), currentExercise);
            }
        }

        for (WorkoutTemplateExercise workoutTemplateExercise : createdWorkoutTemplate.getTemplateExercises()) {
            workoutTemplateExercise.setTemplate(createdWorkoutTemplate);
            workoutTemplateExercise.setExercise(allExercises.get(workoutTemplateExercise.getExerciseId()));
            workoutTemplateExerciseRepository.save(workoutTemplateExercise);
        }

        return createdWorkoutTemplate.getId();
    }

    private WorkoutTemplate workoutTemplateDtoToWorkoutTemplate(WorkoutTemplateDto workoutTemplateDto) {
        WorkoutTemplate workoutTemplate = new WorkoutTemplate();
        workoutTemplate.setName(workoutTemplateDto.getName());
        workoutTemplate.setDescription(workoutTemplateDto.getDescription());
        workoutTemplate.setCreatedAt(Instant.now());

        List<WorkoutTemplateExercise> workoutTemplateExercises = new ArrayList<>();

        int exerciseOrder = 1;
        for (WorkoutTemplateExerciseDto workoutTemplateExerciseDto : workoutTemplateDto.getTemplateExercises()) {
            WorkoutTemplateExercise workoutTemplateExercise = new WorkoutTemplateExercise();
            workoutTemplateExercise.setSequenceNumber(exerciseOrder++);
            workoutTemplateExercise.setExerciseId(workoutTemplateExerciseDto.getExerciseId());
            workoutTemplateExercise.setRest(workoutTemplateExerciseDto.getRest());
            workoutTemplateExercises.add(workoutTemplateExercise);
        }

        workoutTemplate.setTemplateExercises(workoutTemplateExercises);
        return workoutTemplate;
    }

    public WorkoutTemplateDto getWorkoutTemplate(Long id) {
        WorkoutTemplate workoutTemplate = workoutTemplateRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Workout with id " + id + " not found"));
        return workoutTemplateToWorkoutTemplateDto(workoutTemplate);
    }

    public List<WorkoutTemplateDto> getWorkoutTemplates() {
        List<WorkoutTemplate> workouts = workoutTemplateRepository.findAllByUserIdOrderByCreatedAtDesc(userService.getCurrentUser().getId());
        return workouts.stream().map(this::workoutTemplateToWorkoutTemplateDto).collect(Collectors.toList());
    }

    private WorkoutTemplateDto workoutTemplateToWorkoutTemplateDto(WorkoutTemplate workoutTemplate) {
        WorkoutTemplateDto workoutTemplateDto = new WorkoutTemplateDto();
        workoutTemplateDto.setId(workoutTemplate.getId());
        workoutTemplateDto.setName(workoutTemplate.getName());
        workoutTemplateDto.setDescription(workoutTemplate.getDescription());

        List<WorkoutTemplateExerciseDto> workoutTemplateExercisesDto = new ArrayList<>();

        for (WorkoutTemplateExercise workoutTemplateExercise : workoutTemplate.getTemplateExercises()) {

            WorkoutTemplateExerciseDto workoutTemplateExerciseDto = new WorkoutTemplateExerciseDto();
            workoutTemplateExerciseDto.setRest(workoutTemplateExercise.getRest());
            workoutTemplateExerciseDto.setExerciseId(workoutTemplateExercise.getExerciseId());
            workoutTemplateExerciseDto.setSequenceNumber(workoutTemplateExercise.getSequenceNumber());

            workoutTemplateExercisesDto.add(workoutTemplateExerciseDto);
        }

        workoutTemplateDto.setTemplateExercises(workoutTemplateExercisesDto);
        return workoutTemplateDto;
    }
}
