package com.zeussd.gym_tracker.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zeussd.gym_tracker.entities.User;


@Repository
public interface  UserRepository extends JpaRepository<User, UUID> {
    List<User> findByUserNameContainingIgnoringCase(String userName);
}
