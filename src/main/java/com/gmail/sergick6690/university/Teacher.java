package com.gmail.sergick6690.university;

import java.util.List;
import java.util.Objects;

public class Teacher extends Human {
    private String degree;
    private Schedule schedule;
    private List<Subject> subjects;

    public Teacher(int id, String firstName, String lastNAme, String sex, int age, String degree, Schedule schedule, List<Subject> subjects) {
        super(id, firstName, lastNAme, sex, age);
        this.degree = degree;
        this.schedule = schedule;
        this.subjects = subjects;
    }

    public Teacher() {
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
        return "Teacher{" +
                "degree='" + degree + '\'' +
                ", schedule=" + schedule +
                ", subjects=" + subjects +
                '}';
    }
}