package com.leonpahole.workoutapp.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExercisePerformedDto {

    @NotNull
    Long exerciseId;

    @NotNull
    List<ExercisePerformedSetsDto> sets;
}