package com.zeussd.gym_tracker.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zeussd.gym_tracker.entities.Exercise;
import com.zeussd.gym_tracker.repository.ExerciseRepository;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	

	@GetMapping("/paginated")
	public ResponseEntity<Page<Exercise>> getAllExercisesPaginated(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "exerciseName") String sortBy,
		@RequestParam(defaultValue = "asc") String direction
		
	){
		Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		Page<Exercise> exercises = exerciseRepository.findAll(pageable);
		return ResponseEntity.ok(exercises);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Exercise> getExerciseById(@PathVariable(value = "id") UUID id) {
		Optional<Exercise> exercise = exerciseRepository.findById(id);

		if (exercise.isPresent()) {
			return ResponseEntity.ok(exercise.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/search")
	public ResponseEntity<List<Exercise>> searchExercises(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String exerciseName,
			@RequestParam(required = false) String exerciseDescription,
			@RequestParam(required = false) String equipamentType) {

		List<Exercise> exercises = exerciseRepository.findAll();
		return ResponseEntity.ok(exercises);
	}
	

	@PutMapping("/{id}")
	public ResponseEntity<Exercise> updateExerciseById(@PathVariable(value = "id") UUID id,
			@RequestBody Exercise exerciseDetails) {
		Optional<Exercise> searchedExercise = exerciseRepository.findById(id);

		if (searchedExercise.isPresent()) {
			Exercise exercise = searchedExercise.get();

			exercise.setExerciseName(exerciseDetails.getExerciseName());
			exercise.setExerciseDescription(exerciseDetails.getExerciseDescription());
			exercise.setExerciseIcon(exerciseDetails.getExerciseIcon());
			exercise.setEquipamentType(exerciseDetails.getEquipamentType());

			Exercise updatedExercise = exerciseRepository.save(exercise);
			return ResponseEntity.ok(updatedExercise);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
		Exercise savedExercise = exerciseRepository.save(exercise);
		return ResponseEntity.ok(savedExercise);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteExercise(@PathVariable(value = "id") UUID id) {
		Optional<Exercise> exercise = exerciseRepository.findById(id);

		if (exercise.isPresent()) {

			exerciseRepository.delete(exercise.get());
			return ResponseEntity.noContent().build();

		} else {
			return ResponseEntity.notFound().build();
		}

	}

}
