package com.leonpahole.workoutapp.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String comment;

    @NotNull
    private Instant createdAt;

    @NotNull
    @Column(columnDefinition = "DATE")
    private Instant startDate;

    @Column(columnDefinition = "TIME")
    private Instant startTime;

    @Column(columnDefinition = "TIME")
    private Instant endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("order asc")
    @JoinColumn(name = "workout_id")
    private List<ExercisePerformed> exercisesPerformed;
}