package com.gmail.sergick6690;

import java.util.Objects;

public class Audience {
    private int id;
    private int number;

    public Audience(int id, int number) {
        this.id = id;
        this.number = number;
    }

    public Audience() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Audience)) return false;
        Audience audience = (Audience) o;
        return getId() == audience.getId() &&
                getNumber() == audience.getNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber());
    }

    @Override
    public String toString() {
        return "Audience{" +
                "id=" + id +
                ", number=" + number +
                '}';
    }
}
