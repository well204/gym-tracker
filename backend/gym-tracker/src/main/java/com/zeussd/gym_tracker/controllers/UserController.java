package com.zeussd.gym_tracker.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zeussd.gym_tracker.entities.User;
import com.zeussd.gym_tracker.repository.UserRepository;

@RestController
@RequestMapping(value ="/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
    return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") UUID id) {
        Optional<User> userData = userRepository.findById(id);
        
        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
    return ResponseEntity.ok(savedUser);
}
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable ("id")UUID id, @RequestBody User user) {
        Optional<User> userData = userRepository.findById(id);
        
        if(userData.isPresent()) {
            User existingUser = userData.get();
            existingUser.setPassword(user.getPassword());
            existingUser.setUserName(user.getUserName());
            existingUser.setUserHeight(user.getUserHeight());
            existingUser.setUserWeight(user.getUserWeight());

            return new ResponseEntity<>(userRepository.save(existingUser), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserName(@RequestParam(required=false)String userName) {
        List<User> users = userRepository.findByUserNameContainingIgnoringCase(userName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/paginated")
	public ResponseEntity<Page<User>> getAllUsersPaginated(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "userName") String sortBy,
		@RequestParam(defaultValue = "asc") String direction
		
	){
		Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
		Page<User> users = userRepository.findAll(pageable);
		return ResponseEntity.ok(users);
	}
    
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") UUID id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}