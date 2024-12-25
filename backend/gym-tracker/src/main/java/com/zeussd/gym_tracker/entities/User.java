package com.zeussd.gym_tracker.entities;


import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String userName;
    private String password;
    private Double userWeight;
    private Double userHeight;
    private List<Workout> workouts;

    public User() {}

    public User(Long userId, String userName, String password, Double userWeight,
                Double userHeight) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userWeight = userWeight;
        this.userHeight = userHeight;
    }

    public String getUserName() {
        return userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(Double userHeight) {
        this.userHeight = userHeight;
    }

    public Double getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(Double userWeight) {
        this.userWeight = userWeight;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void shareProfile() {}

    public void logout() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }
}
