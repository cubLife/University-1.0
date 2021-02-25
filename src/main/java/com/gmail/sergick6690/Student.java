package com.gmail.sergick6690;

import java.util.Objects;

public class Student extends Human {
    private int course;

    public Student(int id, String firstName, String lastNAme, String sex, int age, int course) {
        super(id, firstName, lastNAme, sex, age);
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

    @Override
    public String toString() {
        return "Student{" +
                "course=" + course +
                '}';
    }
}
