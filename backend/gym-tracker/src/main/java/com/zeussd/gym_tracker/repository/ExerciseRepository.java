package com.zeussd.gym_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeussd.gym_tracker.entities.Exercise;


@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer>{
	
}
