package com.zeussd.gym_tracker.entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Workout implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;
    private long workoutId;
    private String workoutName;
    private long duration;
    private boolean finished;
    private List<Exercise> exercises;
}
