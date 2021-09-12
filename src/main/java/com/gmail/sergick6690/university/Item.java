package com.gmail.sergick6690.university;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectId")
    private Subject subject;
    private String day;
    private int hour;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audienceId")
    private Audience audience;
    private int duration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;

    public Item() {
    }


    public Item(int id, Subject subject, String day, int hour, Audience audience, int duration, Schedule schedule) {
        this.id = id;
        this.subject = subject;
        this.day = day;
        this.hour=hour;
        this.audience = audience;
        this.duration = duration;
        this.schedule = schedule;
    }

    public Item(Subject subject, String day, int hour, Audience audience, int duration, Schedule schedule) {
        this.subject = subject;
        this.day = day;
        this.hour=hour;
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