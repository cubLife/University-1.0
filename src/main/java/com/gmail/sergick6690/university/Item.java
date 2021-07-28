package com.gmail.sergick6690.university;

import java.util.Date;
import java.util.Objects;

public class Item {
    private int id;
    private int subjectId;
    private String day;
    private int hour;
    private int audienceId;
    private int duration;
    private int scheduleId;

    public Item() {
    }


    public Item(int id, int subjectId, String day, int hour, int audienceId, int duration, int scheduleId) {
        this.id = id;
        this.subjectId = subjectId;
        this.day = day;
        this.hour=hour;
        this.audienceId = audienceId;
        this.duration = duration;
        this.scheduleId = scheduleId;
    }

    public Item(int subjectId, String day, int hour, int audienceId, int duration, int scheduleId) {
        this.subjectId = subjectId;
        this.day = day;
        this.hour=hour;
        this.audienceId = audienceId;
        this.duration = duration;
        this.scheduleId = scheduleId;
    }

    public Item(int subjectId, int audienceId) {
        this.subjectId = subjectId;
        this.audienceId = audienceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subject) {
        this.subjectId = subject;
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

    public int getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(int audience) {
        this.audienceId = audience;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int schedule) {
        this.scheduleId = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getId() == item.getId() &&
                getDuration() == item.getDuration() &&
                Objects.equals(getSubjectId(), item.getSubjectId()) &&
                Objects.equals(getDay(), item.getDay()) &&
                Objects.equals(getAudienceId(), item.getAudienceId()) &&
                Objects.equals(getScheduleId(), item.getScheduleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSubjectId(), getDay(), getAudienceId(), getDuration(), getScheduleId());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", subjectId=" + subjectId +
                ", day='" + day + '\'' +
                ", hour=" + hour +
                ", audienceId=" + audienceId +
                ", duration=" + duration +
                ", scheduleId=" + scheduleId +
                '}';
    }
}