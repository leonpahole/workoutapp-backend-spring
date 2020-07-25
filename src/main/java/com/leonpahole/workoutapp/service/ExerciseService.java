package com.leonpahole.workoutapp.service;

import java.time.Instant;
import java.util.List;

import com.leonpahole.workoutapp.dto.CreateExerciseRequest;
import com.leonpahole.workoutapp.dto.ExerciseDto;
import com.leonpahole.workoutapp.errors.ApplicationException;
import com.leonpahole.workoutapp.model.Exercise;
import com.leonpahole.workoutapp.model.ExerciseCategory;
import com.leonpahole.workoutapp.model.User;
import com.leonpahole.workoutapp.repository.ExerciseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final UserService userService;

    @Transactional
    public ExerciseDto createExercise(CreateExerciseRequest createExerciseRequest) {
        Exercise exercise = Exercise.builder()
                .name(createExerciseRequest.getName())
                .author(userService.getCurrentUser())
                .category(ExerciseCategory.of(createExerciseRequest.getCategory())).createdAt(Instant.now()).build();
        exerciseRepository.save(exercise);
        return exerciseToExerciseDto(exercise);
    }

    // get all public exercises (without author) and user's created exercises
    @Transactional
    public List<ExerciseDto> getAllExercises() {
        return exerciseRepository.findAllByAuthorIdOrNullAuthorId(userService.getCurrentUser().getId());
    }

    @Transactional
    public ExerciseDto getExerciseById(Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ApplicationException("Exercise with id " + exerciseId + " not found"));
        return exerciseToExerciseDto(exercise);
    }

    @Transactional
    private ExerciseDto exerciseToExerciseDto(Exercise exercise) {
        User author = exercise.getAuthor();
        return ExerciseDto.builder()
                .name(exercise.getName())
                .id(exercise.getId())
                .authorId(author == null ? null : author.getId())
                .createdAt(exercise.getCreatedAt()).category(exercise.getCategory().getCode()).build();
    }

    public void deleteExerciseById(Long exerciseId) {
        Exercise exercise = exerciseRepository.findByIdAndAuthorId(exerciseId, userService.getCurrentUser().getId())
                .orElseThrow(() -> new ApplicationException("Exercise not found or you are unatuhorized to delete it"));
        exerciseRepository.delete(exercise);
    }
}