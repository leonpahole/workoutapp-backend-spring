package com.leonpahole.workoutapp.repository;

import com.leonpahole.workoutapp.model.ExercisePerformedSet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExercisePerformedSetRepository extends JpaRepository<ExercisePerformedSet, Long> {

    @Query("DELETE FROM ExercisePerformedSet eps WHERE eps.exercisePerformed.id IN (:ids)")
    void deleteAllByExercisePerformedIds(List<Long> exercisePerformedIds);
}