package com.leonpahole.workoutapp.repository;

import java.util.List;

import com.leonpahole.workoutapp.model.Workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findAllByUserIdOrderByCreatedAtDesc(Long id);
}