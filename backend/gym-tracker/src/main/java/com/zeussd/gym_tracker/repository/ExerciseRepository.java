package com.zeussd.gym_tracker.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zeussd.gym_tracker.entities.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, UUID>{
	List<Exercise> findByExerciseNameContainingIgnoreCase(String exerciseName);
	
	List<Exercise> findByExerciseDescriptionContainingIgnoreCase(String exerciseDescription);
	
	List<Exercise> findByEquipamentTypeContainingIgnoreCase(String equipamentType);

	@Query("SELECT e FROM Exercise e WHERE " +
	"LOWER(e.exerciseName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	"LOWER(e.exerciseDescription) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	"LOWER(e.equipamentType) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	List<Exercise> searchByKeyword(@Param("searchTerm") String searchTerm);
	
}
