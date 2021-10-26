package com.gmail.sergick6690.universityModels;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@AllArgsConstructor
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "First name can't be empty")
    @Size(min = 3, message = "First name size can't be less than 3 characters")
    private String firstName;
    @NotBlank(message = "Last name can't be empty")
    @Size(min = 3, message = "Last name size can't be less than 3 characters")
    private String lastName;
    @NotBlank(message = "Sex can't be empty")
    @Size(min = 3, max = 5, message = "Sex size should be between 3 and 5 characters")
    private String sex;
    @Min(value = 1, message = "Age should be more than 0")
    private int age;

    public Human() {
    }

    public Human(String firstName, String lastName, String sex, int age) {
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastNAme) {
        this.lastName = lastNAme;
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
                Objects.equals(getLastName(), human.getLastName()) &&
                Objects.equals(getSex(), human.getSex());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getSex(), getAge());
    }

    @Override
    public String toString() {
        return "Human{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastNAme='" + lastName + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }
}
