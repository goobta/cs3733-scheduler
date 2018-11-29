package com.lesath.apps.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class Schedule {
	String uuid;
	String name;
	int duration;
	LocalDate start_date;
	LocalDate end_date;
	LocalTime daily_start_time;
	LocalTime daily_end_time;
	LocalDateTime created_at;
	LocalDateTime deleted_at;
	/**
	 * @param uuid SHOLD BE NULL
	 * @param name
	 * @param duration
	 * @param start_date
	 * @param end_date
	 * @param daily_start_time
	 * @param daily_end_time
	 * @param created_at
	 * @param deleted_at
	 */
	public Schedule(String uuid, String name, int duration, LocalDate start_date, LocalDate end_date,
			LocalTime daily_start_time, LocalTime daily_end_time, LocalDateTime created_at, LocalDateTime deleted_at) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.duration = duration;
		this.start_date = start_date;
		this.end_date = end_date;
		this.daily_start_time = daily_start_time;
		this.daily_end_time = daily_end_time;
		this.created_at = created_at;
		this.deleted_at = deleted_at;
	}
	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	/**
	 * @return the start_date
	 */
	public LocalDate getStart_date() {
		return start_date;
	}
	/**
	 * @param start_date the start_date to set
	 */
	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}
	/**
	 * @return the end_date
	 */
	public LocalDate getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date the end_date to set
	 */
	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}
	/**
	 * @return the daily_start_time
	 */
	public LocalTime getDaily_start_time() {
		return daily_start_time;
	}
	/**
	 * @param daily_start_time the daily_start_time to set
	 */
	public void setDaily_start_time(LocalTime daily_start_time) {
		this.daily_start_time = daily_start_time;
	}
	/**
	 * @return the daily_end_time
	 */
	public LocalTime getDaily_end_time() {
		return daily_end_time;
	}
	/**
	 * @param daily_end_time the daily_end_time to set
	 */
	public void setDaily_end_time(LocalTime daily_end_time) {
		this.daily_end_time = daily_end_time;
	}
	/**
	 * @return the created_at
	 */
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	/**
	 * @return the deleted_at
	 */
	public LocalDateTime getDeleted_at() {
		return deleted_at;
	}
	/**
	 * @param deleted_at the deleted_at to set
	 */
	public void setDeleted_at(LocalDateTime deleted_at) {
		this.deleted_at = deleted_at;
	}
}
