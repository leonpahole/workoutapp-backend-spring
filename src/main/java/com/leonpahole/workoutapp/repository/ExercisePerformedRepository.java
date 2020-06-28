package com.leonpahole.workoutapp.repository;

import com.leonpahole.workoutapp.model.ExercisePerformed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExercisePerformedRepository extends JpaRepository<ExercisePerformed, Long> {

}