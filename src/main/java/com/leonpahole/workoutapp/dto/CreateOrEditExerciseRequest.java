package com.leonpahole.workoutapp.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrEditExerciseRequest {
    @NotNull
    private String name;

    @NotNull
    private String category;
}