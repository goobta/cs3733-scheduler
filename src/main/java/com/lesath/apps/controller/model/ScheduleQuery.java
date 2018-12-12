package com.lesath.apps.controller.model;

import java.time.LocalDateTime;

public class ScheduleQuery {
    Integer month;
    Integer day;
    Integer year;
    Integer dayOfTheWeek;
    LocalDateTime startTime;
    LocalDateTime endTime;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(int dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
