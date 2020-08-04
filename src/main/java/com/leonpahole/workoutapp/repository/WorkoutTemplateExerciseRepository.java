package com.leonpahole.workoutapp.repository;

import com.leonpahole.workoutapp.model.WorkoutTemplateExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutTemplateExerciseRepository extends JpaRepository<WorkoutTemplateExercise, Long> {
}
