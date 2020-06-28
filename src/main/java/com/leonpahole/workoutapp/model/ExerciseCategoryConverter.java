package com.leonpahole.workoutapp.model;

import java.util.Arrays;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ExerciseCategoryConverter implements AttributeConverter<ExerciseCategory, String> {

    @Override
    public String convertToDatabaseColumn(ExerciseCategory category) {
        if (category == null) {
            return null;
        }

        return category.getCode();
    }

    @Override
    public ExerciseCategory convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Arrays.stream(ExerciseCategory.values()).filter(c -> c.getCode().equals(code)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}