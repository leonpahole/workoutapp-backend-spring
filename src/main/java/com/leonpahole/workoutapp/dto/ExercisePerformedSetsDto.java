package com.leonpahole.workoutapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExercisePerformedSetsDto {
    private Integer repetitions;
    private Integer time;
    private Double weight;
    private String weightUnit;
}