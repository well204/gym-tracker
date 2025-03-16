package com.zeussd.gym_tracker.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table (name = "users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="userId")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;
    private String userName;
    private String password;
    private Double userWeight;
    private Double userHeight;
    
    @OneToMany(mappedBy="user")
    private List<Workout> workouts;
}
