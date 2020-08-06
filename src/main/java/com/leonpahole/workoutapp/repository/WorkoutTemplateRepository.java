package com.leonpahole.workoutapp.repository;

import com.leonpahole.workoutapp.model.Workout;
import com.leonpahole.workoutapp.model.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate, Long> {
    List<WorkoutTemplate> findAllByUserIdOrderByCreatedAtDesc(Long id);

    Optional<WorkoutTemplate> findByIdAndUserId(Long id, Long userId);
}
