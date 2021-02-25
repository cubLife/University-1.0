package com.gmail.sergick6690;

import java.util.List;
import java.util.Objects;

public class Schedule {
    private int id;
    private List<Item> items;

    public Schedule(int id, List<Item> items) {
        this.id = id;
        this.items = items;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return getId() == schedule.getId() &&
                Objects.equals(getItems(), schedule.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getItems());
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", items=" + items +
                '}';
    }
}
