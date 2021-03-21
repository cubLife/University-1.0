package com.gmail.sergick6690;

import java.util.List;
import java.util.Objects;

public class Faculty {
    private int id;
    private String name;
    private List<Cathedra> cathedra;

    public Faculty() {
    }

    public Faculty(int id, String name, List<Cathedra> cathedra) {
        this.id = id;
        this.name = name;
        this.cathedra = cathedra;
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

    public List<Cathedra> getCathedra() {
        return cathedra;
    }

    public void setCathedra(List<Cathedra> cathedra) {
        this.cathedra = cathedra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty)) return false;
        Faculty faculty = (Faculty) o;
        return getId() == faculty.getId() &&
                Objects.equals(getName(), faculty.getName()) &&
                Objects.equals(getCathedra(), faculty.getCathedra());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCathedra());
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cathedra=" + cathedra +
                '}';
    }
}
