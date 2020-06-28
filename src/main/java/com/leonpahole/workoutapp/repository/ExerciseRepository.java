package com.leonpahole.workoutapp.repository;

import java.util.List;
import java.util.Optional;

import com.leonpahole.workoutapp.dto.ExerciseDto;
import com.leonpahole.workoutapp.model.Exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @Query("SELECT NEW com.leonpahole.workoutapp.dto.ExerciseDto(e.id, e.name, e.description, e.photoUrl, e.category, e.author.id, e.createdAt) FROM Exercise e WHERE e.author.id = :authorId OR e.author IS NULL ORDER BY e.createdAt DESC")
    List<ExerciseDto> findAllByAuthorIdOrNullAuthorId(@Param("authorId") Long authorId);

    Optional<Exercise> findByIdAndAuthorId(Long id, Long authorId);
}