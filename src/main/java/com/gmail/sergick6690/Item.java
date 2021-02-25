package com.gmail.sergick6690;

import javax.xml.datatype.Duration;
import java.util.Date;
import java.util.Objects;

public class Item {
    private int id;
    private Subject subject;
    private Date date;
    private Audience audience;
    private Duration duration;

    public Item(int id, Subject subject, Date date, Audience audience, Duration duration) {
        this.id = id;
        this.subject = subject;
        this.date = date;
        this.audience = audience;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getId() == item.getId() &&
                Objects.equals(getSubject(), item.getSubject()) &&
                Objects.equals(getDate(), item.getDate()) &&
                Objects.equals(getAudience(), item.getAudience()) &&
                Objects.equals(getDuration(), item.getDuration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSubject(), getDate(), getAudience(), getDuration());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", subject=" + subject +
                ", date=" + date +
                ", audience=" + audience +
                ", duration=" + duration +
                '}';
    }
}