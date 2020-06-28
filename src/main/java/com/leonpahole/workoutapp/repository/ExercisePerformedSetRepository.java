package com.leonpahole.workoutapp.repository;

import com.leonpahole.workoutapp.model.ExercisePerformedSet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExercisePerformedSetRepository extends JpaRepository<ExercisePerformedSet, Long> {

}