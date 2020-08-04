package com.leonpahole.workoutapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutTemplateDto {

    Long id;

    @NotNull
    String name;

    @NotNull
    String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd. MM. yyyy HH:mm")
    private Date createdAt;

    @NotNull
    private List<WorkoutTemplateExerciseDto> templateExercises;
}
