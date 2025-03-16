package com.zeussd.gym_tracker.entities;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sets")
public class Set {
    @Id
    @GeneratedValue
    private UUID id; 
    private Integer reps;
    private String notes;
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name="workouts")
    private Workout workout;

}
