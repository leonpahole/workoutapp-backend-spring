package com.leonpahole.workoutapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutTemplateExerciseDto {

    private Integer rest;

    @NotNull
    private Integer sequenceNumber;

    @NotNull
    private Long exerciseId;
}
