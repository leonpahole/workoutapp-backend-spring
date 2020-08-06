package com.leonpahole.workoutapp.repository;

import com.leonpahole.workoutapp.model.WorkoutTemplateExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface WorkoutTemplateExerciseRepository extends JpaRepository<WorkoutTemplateExercise, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM WorkoutTemplateExercise wte WHERE wte.template.id = :id")
    void deleteByWorkoutTemplateId(Long id);
}
