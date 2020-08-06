package com.leonpahole.workoutapp.controller;

import java.util.List;

import javax.validation.Valid;

import com.leonpahole.workoutapp.dto.WorkoutDto;
import com.leonpahole.workoutapp.service.WorkoutService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;


@RestController
@RequestMapping("/workout")
@AllArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @Data
    @AllArgsConstructor
    public static class CreateWorkoutResponse {
        Long workoutId;
        Long templateId;
    }

    @PostMapping
    public ResponseEntity<CreateWorkoutResponse> createWorkout(@RequestBody @Valid WorkoutDto workout) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutService.createWorkout(workout));
    }

    @GetMapping
    public ResponseEntity<List<WorkoutDto>> getWorkouts() {
        return ResponseEntity.status(HttpStatus.OK).body(workoutService.getWorkouts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDto> getWorkoutById(@PathVariable("id") Long workoutId) {
        return ResponseEntity.status(HttpStatus.OK).body(workoutService.getWorkout(workoutId));
    }
}