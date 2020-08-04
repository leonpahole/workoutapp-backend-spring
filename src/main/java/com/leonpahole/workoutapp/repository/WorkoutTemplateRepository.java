package com.leonpahole.workoutapp.repository;

import com.leonpahole.workoutapp.model.Workout;
import com.leonpahole.workoutapp.model.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate, Long> {
    List<WorkoutTemplate> findAllByUserIdOrderByCreatedAtDesc(Long id);
}
