package com.zeussd.gym_tracker.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="workouts")
public class Workout {
    @Id
    @GeneratedValue
    private UUID id;
    private String workoutName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean finished;
    
    @OneToMany(mappedBy = "workout")
    private List<Set> sets;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public long getDuration() {
        if (startTime == null)
            return 0;
        LocalDateTime end = (endTime != null) ? endTime : LocalDateTime.now();
        return  Duration.between(startTime, end).toMillis();
    }
}
