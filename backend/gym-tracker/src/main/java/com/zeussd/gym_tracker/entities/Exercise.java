package com.zeussd.gym_tracker.entities;

import java.io.Serial;
import java.io.Serializable;

public class Exercise implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private int exerciseId;
    private String exerciseName;
    private String exerciseDescription;
    private String exerciseIcon;
    private String equipamentType;
    private Integer reps;
    private Integer prevReps;
    private Integer set;
}
