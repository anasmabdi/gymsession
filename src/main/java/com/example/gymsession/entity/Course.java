package com.example.gymsession.entity;

import java.util.List;
import java.util.Objects;

public class Course {
    private int id;
    private String name;
    private String description;
    private Trainer trainer;
    private List<Trainee> trainee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public List<Trainee> getTrainee() {
        return trainee;
    }

    public void setTrainees(List<Trainee> trainee) {
        this.trainee = trainee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return getId() == course.getId() && getName().equals(course.getName()) && Objects.equals(getDescription(), course.getDescription()) && getTrainer().equals(course.getTrainer()) && Objects.equals(getTrainee(), course.getTrainee());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getTrainer(), getTrainee());
    }
}
