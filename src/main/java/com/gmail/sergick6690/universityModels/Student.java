package com.gmail.sergick6690.universityModels;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "students")
public class Student extends Human {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    @JsonBackReference
    private Group group;
    @NotNull
    @Min(value = 1, message = "Course can't be less than 1")
    @Max(value = 7, message = "Course can't be bigger than 7")
    private int course;

    public Student() {
    }

    @Builder
    private Student(int id, String firstName, String lastNAme, String sex, int age, int course, Group group) {
        super(id, firstName, lastNAme, sex, age);
        this.group = group;
        this.course = course;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return getCourse() == student.getCourse();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), group, getCourse());
    }

    @Override
    public String toString() {
        return super.toString() + "Student{" +
                "group=" + group +
                ", course=" + course +
                '}';
    }
}
