package com.leonpahole.workoutapp.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutDto {

    private Long id;

    private Boolean saveAsTemplate = false;

    private String name;

    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd. MM. yyyy HH:mm")
    private Date createdAt;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd. MM. yyyy")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Date startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Date endTime;

    private List<ExercisePerformedDto> exercisesPerformed;
}