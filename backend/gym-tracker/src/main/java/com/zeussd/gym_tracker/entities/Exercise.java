package com.zeussd.gym_tracker.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "exercises")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Exercise {
    @Id
    @GeneratedValue
    private UUID id;
    private String exerciseName;
    private String exerciseDescription;
    private String exerciseIcon;
    private String equipamentType;
}
