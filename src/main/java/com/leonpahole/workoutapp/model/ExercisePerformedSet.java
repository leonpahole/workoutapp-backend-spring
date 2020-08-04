package com.leonpahole.workoutapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExercisePerformedSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_performed_id", nullable = false)
    private ExercisePerformed exercisePerformed;

    @NotNull
    @Column(name = "set_order")
    private Integer order;

    private Integer repetitions;
    private Integer time; // in seconds ALWAYS

    private Double weight;

    private Integer rest; // in seconds ALWAYS
}