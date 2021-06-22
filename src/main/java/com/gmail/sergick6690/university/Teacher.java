package com.gmail.sergick6690.university;

import lombok.Builder;

import java.util.List;
import java.util.Objects;

public class Teacher extends Human {
    private String degree;
    private int scheduleId;
    private List<Subject> subjects;

    public Teacher() {
    }

    @Builder
    private Teacher(int id, String firstName, String lastNAme, String sex, int age, String degree, int scheduleId, List<Subject> subjects) {
        super(id, firstName, lastNAme, sex, age);
        this.degree = degree;
        this.scheduleId = scheduleId;
        this.subjects = subjects;
    }


    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
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
                Objects.equals(getScheduleId(), teacher.getScheduleId()) &&
                Objects.equals(getSubjects(), teacher.getSubjects());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDegree(), getScheduleId(), getSubjects());
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "degree='" + degree + '\'' +
                ", schedule=" + scheduleId +
                ", subjects=" + subjects +
                '}';
    }
}
