package com.gmail.sergick6690.university;

import java.util.List;
import java.util.Objects;

public class Group {
    private int id;
    private String name;
    private List<Student> students;
    private int scheduleId;
    private int cathedraId;

    public Group(int id, String name, List<Student> students, int scheduleId, int cathedraId) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.scheduleId = scheduleId;
        this.cathedraId = cathedraId;
    }

    public Group(String name, int scheduleId, int cathedraId) {
        this.name = name;
        this.scheduleId = scheduleId;
        this.cathedraId = cathedraId;
    }

    public Group() {
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

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getCathedraId() {
        return cathedraId;
    }

    public void setCathedraId(int cathedraId) {
        this.cathedraId = cathedraId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return getId() == group.getId() &&
                getScheduleId() == group.getScheduleId() &&
                getCathedraId() == group.getCathedraId() &&
                Objects.equals(getName(), group.getName()) &&
                Objects.equals(getStudents(), group.getStudents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getStudents(), getScheduleId(), getCathedraId());
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" + students +
                ", scheduleId=" + scheduleId +
                ", cathedraId=" + cathedraId +
                '}';
    }
}
