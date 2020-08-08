package com.leonpahole.workoutapp.controller;

import com.leonpahole.workoutapp.dto.UserProfile;
import com.leonpahole.workoutapp.model.Exercise;
import com.leonpahole.workoutapp.service.ExerciseService;
import com.leonpahole.workoutapp.service.StatisticsService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/statistics")
@AllArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GetStatisticsResponse {
        private long workoutsCount;
        private List<Long> popularExercises;
        private long totalCardioTimeSeconds;
        private HashMap<Long, Long> totalRepsPerBodyweightExercise;
        private HashMap<Long, Long> totalWeightPerStrengthExercise;
        private HashMap<Long, Long> totalWeightPerCardioExercise;
    }

    @GetMapping
    public ResponseEntity<GetStatisticsResponse> getStatistics() {
        return ResponseEntity.status(HttpStatus.OK).body(statisticsService.getStatistics());
    }
}
