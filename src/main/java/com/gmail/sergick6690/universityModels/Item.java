package com.gmail.sergick6690.universityModels;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectId")
    @JsonBackReference
    private Subject subject;
    @NotBlank(message = "Day can't be empty")
    private String day;
    @NotNull
    @Min(value = 9, message = "Hour can't be less than 9")
    @Max(value = 17, message = "Hour can't be bigger than 17")
    private int hour;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audienceId")
    @JsonBackReference
    private Audience audience;
    @Min(1)
    @Max(2)
    private int duration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduleId")
    @JsonBackReference
    private Schedule schedule;

    public Item() {
    }


    public Item(int id, Subject subject, String day, int hour, Audience audience, int duration, Schedule schedule) {
        this.id = id;
        this.subject = subject;
        this.day = day;
        this.hour = hour;
        this.audience = audience;
        this.duration = duration;
        this.schedule = schedule;
    }

    public Item(Subject subject, String day, int hour, Audience audience, int duration, Schedule schedule) {
        this.subject = subject;
        this.day = day;
        this.hour = hour;
        this.audience = audience;
        this.duration = duration;
        this.schedule = schedule;
    }

    public Item(Subject subject, Audience audience) {
        this.subject = subject;
        this.audience = audience;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getId() == item.getId() &&
                getHour() == item.getHour() &&
                getDuration() == item.getDuration() &&
                Objects.equals(getDay(), item.getDay());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDay(), getHour(), getDuration());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", subject=" + subject +
                ", day='" + day + '\'' +
                ", hour=" + hour +
                ", audience=" + audience +
                ", duration=" + duration +
                ", schedule=" + schedule +
                '}';
    }
}