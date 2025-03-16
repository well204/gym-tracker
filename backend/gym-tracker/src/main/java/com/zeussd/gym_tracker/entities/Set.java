package com.zeussd.gym_tracker.entities;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sets")
public class Set {
    @Id
    @GeneratedValue
    private UUID id; 
    private Integer reps;
    private Integer prevReps;
}
