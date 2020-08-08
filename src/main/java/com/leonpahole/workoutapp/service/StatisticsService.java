package com.leonpahole.workoutapp.service;

import com.leonpahole.workoutapp.controller.StatisticsController;
import com.leonpahole.workoutapp.repository.WorkoutRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatisticsService {

    private final WorkoutRepository workoutRepository;

    public StatisticsController.GetStatisticsResponse getStatistics() {
        return StatisticsController.GetStatisticsResponse.builder()
                .workoutsCount(getWorkoutsCount())
                .build();
    }

    public long getWorkoutsCount() {
        return workoutRepository.count();
    }
}
