package com.zeussd.gym_tracker.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.zeussd.gym_tracker.entities.Exercise;

public class Workout implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long workoutId;
    private String workoutName;
    private long duration;
    private boolean finished;
    private List<Exercise> exercises;
    public Workout() {}
}
