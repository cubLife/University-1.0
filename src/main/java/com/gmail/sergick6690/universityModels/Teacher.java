package com.gmail.sergick6690.universityModels;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "teachers")
public class Teacher extends Human {
    private String degree;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scheduleId")
    @JsonBackReference
    private Schedule schedule;
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<Subject> subjects = new ArrayList<>();

    public Teacher() {
    }

    @Builder
    private Teacher(int id, String firstName, String lastName, String sex, int age, String degree, Schedule schedule, List<Subject> subjects) {
        super(id, firstName, lastName, sex, age);
        this.degree = degree;
        this.schedule = schedule;
        this.subjects = subjects;
    }


    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        if (!super.equals(o)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getDegree(), teacher.getDegree()) &&
                Objects.equals(getSchedule(), teacher.getSchedule()) &&
                Objects.equals(getSubjects(), teacher.getSubjects());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDegree(), getSchedule(), getSubjects());
    }

    @Override
    public String toString() {
        return super.toString() + "Teacher{" +
                "degree='" + degree + '\'' +
                ", schedule=" + schedule +
                ", subjects=" + subjects +
                "} ";
    }
}
