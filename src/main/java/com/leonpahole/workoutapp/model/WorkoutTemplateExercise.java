package com.leonpahole.workoutapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkoutTemplateExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer sequenceNumber;

    private Integer rest;

    @Transient
    private Long exerciseId;

    @ManyToOne
    @JoinColumn(name = "workout_template_id", nullable = false)
    private WorkoutTemplate template;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;
}
