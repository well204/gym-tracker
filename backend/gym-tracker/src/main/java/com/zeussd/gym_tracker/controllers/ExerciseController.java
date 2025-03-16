	package com.zeussd.gym_tracker.controllers;

	import java.util.List;
	import java.util.Optional;
	import java.util.UUID;

	import org.springframework.beans.factory.annotation.Autowired;
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




	@RestController
	@RequestMapping(value = "/exercises")
	public class ExerciseController {
		
		
		// private static final Logger logger = LoggerFactory.getLogger(ExerciseController.class);

		@Autowired
		private ExerciseRepository exerciseRepository;

		@GetMapping
		public ResponseEntity<List<Exercise>> getAllExercises() {
			List<Exercise> exercises = exerciseRepository.findAll();
			return ResponseEntity.ok(exercises);
		}

		@GetMapping("/{id}")
		public ResponseEntity<Exercise> getExerciseById(@PathVariable(value="id") UUID id){
			Optional<Exercise> exercise = exerciseRepository.findById(id);

			if (exercise.isPresent()){
				return ResponseEntity.ok(exercise.get());
			}else{
				return ResponseEntity.notFound().build();
			}
		}

		@PutMapping("/{id}")
		public ResponseEntity<Exercise> updateExerciseById(@PathVariable(value = "id") UUID id, @RequestBody Exercise exerciseDetails) {
			Optional<Exercise> searchedExercise = exerciseRepository.findById(id);

			if(searchedExercise.isPresent()){
				Exercise exercise = searchedExercise.get();
				
				exercise.setExerciseName(exerciseDetails.getExerciseName());
				exercise.setExerciseDescription(exerciseDetails.getExerciseDescription());
				exercise.setExerciseIcon(exerciseDetails.getExerciseIcon());
				exercise.setEquipamentType(exerciseDetails.getEquipamentType());

				Exercise updatedExercise = exerciseRepository.save(exercise);
				return ResponseEntity.ok(updatedExercise);
			}else{
				return ResponseEntity.notFound().build();
			}
		}
		
		@PostMapping
		public ResponseEntity<Exercise> createExercise (@RequestBody Exercise exercise){
			// logger.info("Received exercise: " + exercise.toString());
			Exercise savedExercise = exerciseRepository.save(exercise);
			return ResponseEntity.ok(savedExercise);
		}

		@DeleteMapping("/{id}")
		public ResponseEntity<Void> deleteExercise(@PathVariable(value = "id") UUID id){
			Optional<Exercise> exercise = exerciseRepository.findById(id);

			if (exercise.isPresent()){

				exerciseRepository.delete(exercise.get());
				return ResponseEntity.noContent().build();
			
			}else{
				return ResponseEntity.notFound().build();
			}

		}
		

	}
