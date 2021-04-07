package com.gmail.sergick6690.university;

import java.util.Objects;

public class Human {
    private int id;
    private String firstName;
    private String lastNAme;
    private String sex;
    private int age;

    public Human(int id, String firstName, String lastNAme, String sex, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastNAme = lastNAme;
        this.sex = sex;
        this.age = age;
    }

    public Human(String firstName, String lastNAme, String sex, int age) {
    }

    public Human() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNAme() {
        return lastNAme;
    }

    public void setLastNAme(String lastNAme) {
        this.lastNAme = lastNAme;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Human)) return false;
        Human human = (Human) o;
        return getId() == human.getId() &&
                getAge() == human.getAge() &&
                Objects.equals(getFirstName(), human.getFirstName()) &&
                Objects.equals(getLastNAme(), human.getLastNAme()) &&
                Objects.equals(getSex(), human.getSex());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastNAme(), getSex(), getAge());
    }

    @Override
    public String toString() {
        return "Human{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastNAme='" + lastNAme + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }
}
