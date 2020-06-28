package com.leonpahole.workoutapp.model;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum WeightUnit {
    KILOGRAM("KG"), POUND("LB");

    @Getter
    private String code;

    public static WeightUnit of(String code) {
        return Arrays.stream(WeightUnit.values()).filter(p -> p.getCode().equals(code)).findFirst().orElse(null);
    }
}