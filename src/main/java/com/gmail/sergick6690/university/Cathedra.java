package com.gmail.sergick6690.university;

import java.util.List;
import java.util.Objects;

public class Cathedra {
    private int id;
    private String name;
    private int facultyId;
    private List<Group> groups;

    public Cathedra() {
    }

    public Cathedra(int id, String name, int facultyId, List<Group> groups) {
        this.id = id;
        this.name = name;
        this.facultyId = facultyId;
        this.groups = groups;
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

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cathedra)) return false;
        Cathedra cathedra = (Cathedra) o;
        return getId() == cathedra.getId() &&
                Objects.equals(getName(), cathedra.getName()) &&
                Objects.equals(getGroups(), cathedra.getGroups());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getGroups());
    }

    @Override
    public String toString() {
        return "Cathedra{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", groups=" + groups +
                '}';
    }
}
