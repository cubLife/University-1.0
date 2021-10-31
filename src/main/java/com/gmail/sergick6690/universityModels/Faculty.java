package com.gmail.sergick6690.universityModels;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@JacksonXmlRootElement
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name can't be empty")
    @Size(min = 3, message = "Name size can't be less than 3 characters")
    private String name;
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    @NotNull
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<Cathedra> cathedras = new ArrayList<>();

    public Faculty() {
    }

    public Faculty(int id, String name, List<Cathedra> cathedras) {
        this.id = id;
        this.name = name;
        this.cathedras = cathedras;
    }

    public Faculty(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Faculty(String name) {
        this.name = name;
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

    public List<Cathedra> getCathedras() {
        return cathedras;
    }

    public void setCathedras(List<Cathedra> cathedras) {
        this.cathedras = cathedras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty)) return false;
        Faculty faculty = (Faculty) o;
        return getId() == faculty.getId() &&
                Objects.equals(getName(), faculty.getName()) &&
                Objects.equals(getCathedras(), faculty.getCathedras());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCathedras());
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}