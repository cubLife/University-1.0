package com.gmail.sergick6690.university;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Cathedra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facultyId")
    private Faculty faculty;
    @OneToMany(mappedBy = "cathedra", cascade = CascadeType.ALL)
    private List<Group> groups;

    public Cathedra() {
    }

    public Cathedra(int id, String name, Faculty faculty, List<Group> groups) {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
        this.groups = groups;
    }

    public Cathedra(int id,String name) {
       this.id= id;
        this.name = name;
    }

    public Cathedra(String name, Faculty faculty) {
        this.name = name;
        this.faculty = faculty;
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
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
                getName().equals(cathedra.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getFaculty());
    }

    @Override
    public String toString() {
        return "Cathedra{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
