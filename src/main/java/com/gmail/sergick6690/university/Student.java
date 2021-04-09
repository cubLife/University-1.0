package com.gmail.sergick6690.university;

import lombok.Builder;

import java.util.Objects;

public class Student extends Human {
    private int groupID;
    private int course;

    public Student() {
    }

    @Builder
    private Student(int id, String firstName, String lastNAme, String sex, int age, int course, int groupID) {
        super(id, firstName, lastNAme, sex, age);
        this.groupID = groupID;
        this.course = course;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
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
        return Objects.hash(super.hashCode(), getCourse());
    }


}
