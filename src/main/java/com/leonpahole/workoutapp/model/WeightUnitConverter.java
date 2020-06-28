package com.leonpahole.workoutapp.model;

import java.util.Arrays;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class WeightUnitConverter implements AttributeConverter<WeightUnit, String> {

    @Override
    public String convertToDatabaseColumn(WeightUnit weightUnit) {
        if (weightUnit == null) {
            return null;
        }

        return weightUnit.getCode();
    }

    @Override
    public WeightUnit convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Arrays.stream(WeightUnit.values()).filter(c -> c.getCode().equals(code)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}