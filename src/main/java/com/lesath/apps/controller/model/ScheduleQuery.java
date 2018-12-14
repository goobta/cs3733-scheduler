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

    public ScheduleQuery setMonth(int month) {
        this.month = month;

        return this;
    }

    public Integer getDay() {
        return day;
    }

    public ScheduleQuery setDay(int day) {
        this.day = day;

        return this;
    }

    public Integer getYear() {
        return year;
    }

    public ScheduleQuery setYear(int year) {
        this.year = year;

        return this;
    }

    public Integer getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public ScheduleQuery setDayOfTheWeek(int dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;

        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public ScheduleQuery setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;

        return this;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public ScheduleQuery setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;

        return this;
    }
}
