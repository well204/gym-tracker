package com.zeussd.gym_tracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zeussd.gym_tracker.entities.Exercise;
import com.zeussd.gym_tracker.repository.ExerciseRepository;



@RestController
@RequestMapping(value = "/exercises")
public class ExerciseController {
	
	@Autowired
	private ExerciseRepository exerciseRepository;

	@GetMapping
	public ResponseEntity<List<Exercise>> getAllExercises() {
		List<Exercise> exercises = exerciseRepository.findAll();
		return ResponseEntity.ok(exercises);
	}
	
	@PostMapping
	public ResponseEntity<Exercise> createExercise (@RequestBody Exercise exercise){
		Exercise savedExercise = exerciseRepository.save(exercise);
		
		return ResponseEntity.ok(savedExercise);
	}
	

}
