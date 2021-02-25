package com.gmail.sergick6690;

import java.util.List;
import java.util.Objects;

public class Group {
    private int id;
    private String name;
    private List<Student> students;
    private Schedule schedule;

    public Group(int id, String name, List<Student> students, Schedule schedule) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.schedule = schedule;
    }

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return getId() == group.getId() &&
                Objects.equals(getName(), group.getName()) &&
                Objects.equals(getStudents(), group.getStudents()) &&
                Objects.equals(getSchedule(), group.getSchedule());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getStudents(), getSchedule());
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" + students +
                ", schedule=" + schedule +
                '}';
    }

}
