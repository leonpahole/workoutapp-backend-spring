package com.leonpahole.workoutapp.repository;

import java.util.List;
import java.util.Optional;

import com.leonpahole.workoutapp.model.Workout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findAllByUserIdOrderByCreatedAtDesc(Long id);

    Optional<Workout> findByIdAndUserId(Long id, Long userId);
}