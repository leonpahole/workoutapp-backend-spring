package com.leonpahole.workoutapp.model;

import java.util.Arrays;

import com.leonpahole.workoutapp.errors.ApplicationException;

import lombok.AllArgsConstructor;
import lombok.Getter;

// this is different from exercise type: type is usually not input by user, while category is
// there is a direct mapping from category to type
@AllArgsConstructor
public enum ExerciseCategory {
    STRENGTH("STR"), BODYWEIGHT("BW"), STRETCHING("ST"), CARDIO("CA"), TIMED("TIM");

    @Getter
    private String code;

    public static ExerciseCategory of(String code) {
        return Arrays.stream(ExerciseCategory.values()).filter(p -> p.getCode().equals(code)).findFirst()
                .orElseThrow(() -> new ApplicationException("Code " + code + " is invalid for category"));
    }

}