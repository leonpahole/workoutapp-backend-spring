package com.leonpahole.workoutapp.controller;

import java.util.List;

import javax.validation.Valid;

import com.leonpahole.workoutapp.dto.WorkoutDto;
import com.leonpahole.workoutapp.dto.WorkoutTemplateDto;
import com.leonpahole.workoutapp.service.WorkoutService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{id}")
    public ResponseEntity<CreateWorkoutResponse> updateWorkout(@PathVariable("id") Long workoutId,
                                                                               @RequestBody @Valid WorkoutDto workout) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutService.updateWorkout(workoutId, workout));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CreateWorkoutResponse> deleteWorkout(@PathVariable("id") Long workoutId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutService.deleteWorkout(workoutId));
    }

}