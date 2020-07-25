package com.leonpahole.workoutapp.dto;

import java.time.Instant;

import com.leonpahole.workoutapp.model.ExerciseCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ExerciseDto {

    private Long id;

    private String name;

    private String category;

    private Long authorId;

    private Instant createdAt;

    public ExerciseDto(Long id, String name, ExerciseCategory category,
            Long authorId, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.category = category.getCode();
        this.authorId = authorId;
        this.createdAt = createdAt;
    }

}