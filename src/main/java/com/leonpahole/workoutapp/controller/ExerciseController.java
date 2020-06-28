package com.leonpahole.workoutapp.controller;

import java.util.List;

import javax.validation.Valid;

import com.leonpahole.workoutapp.dto.CreateExerciseRequest;
import com.leonpahole.workoutapp.dto.ExerciseDto;
import com.leonpahole.workoutapp.service.ExerciseService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/exercise")
@AllArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<ExerciseDto> createExercise(@Valid @RequestBody CreateExerciseRequest createExerciseRequest) {
        ExerciseDto createdExercise = exerciseService.createExercise(createExerciseRequest);
        return ResponseEntity.status(HttpStatus.OK).body(createdExercise);
    }

    @GetMapping
    public ResponseEntity<List<ExerciseDto>> getAllExercises() {
        return ResponseEntity.status(HttpStatus.OK).body(exerciseService.getAllExercises());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable("id") Long exerciseId) {
        ExerciseDto exercise = exerciseService.getExerciseById(exerciseId);
        return ResponseEntity.status(HttpStatus.OK).body(exercise);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExerciseById(@PathVariable("id") Long exerciseId) {
        exerciseService.deleteExerciseById(exerciseId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}