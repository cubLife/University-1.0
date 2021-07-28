package com.gmail.sergick6690.university;

import java.util.List;
import java.util.Objects;

public class Schedule {
    private int id;
    private String name;
    private List<Item> items;

    public Schedule() {
    }

    public Schedule(int id, String name, List<Item> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }

    public Schedule(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return getId() == schedule.getId() &&
                Objects.equals(getName(), schedule.getName()) &&
                Objects.equals(getItems(), schedule.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getItems());
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", name=" + name +
                ", items=" + items +
                '}';
    }
}