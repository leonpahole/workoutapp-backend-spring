package com.leonpahole.workoutapp.repository;

import com.leonpahole.workoutapp.model.ExercisePerformed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ExercisePerformedRepository extends JpaRepository<ExercisePerformed, Long> {
    List<ExercisePerformed> findAllByWorkoutId(Long workoutId);

    @Query("DELETE FROM ExercisePerformed ep WHERE ep.workout.id = :id")
    void deleteAllByWorkoutId(Long id);
}