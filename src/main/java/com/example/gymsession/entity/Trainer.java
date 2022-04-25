package com.example.gymsession.entity;

import java.util.Objects;

public class Trainer {
    private int id;
    private String firstName;
    private String lastName;
    private String strength;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trainer)) return false;
        Trainer trainer = (Trainer) o;
        return getId() == trainer.getId() && getFirstName().equals(trainer.getFirstName()) && getLastName().equals(trainer.getLastName()) && Objects.equals(getStrength(), trainer.getStrength());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getStrength());
    }
}