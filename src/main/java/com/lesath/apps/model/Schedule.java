package com.lesath.apps.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class Schedule {
	@SerializedName("id")
	String uuid;
	String name;
	@SerializedName("meetingDuration")
	int duration;
	LocalDate start_date;
	LocalDate end_date;
	LocalTime daily_start_time;
	LocalTime daily_end_time;
	LocalDateTime created_at;
	LocalDateTime deleted_at;
	
	String organizerId = "01e113d8-3cc3-4fc1-90a2-81f65cfcda16";
	
	LocalDateTime startDateTime;
	LocalDateTime endDateTime;
	/**
	 * @param uuid SHOULD BE NULL
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
		
		this.startDateTime = LocalDateTime.of(start_date, daily_start_time);
		this.endDateTime = LocalDateTime.of(end_date, daily_end_time);
		
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
	/**
	 * @return the organizerId
	 */
	public String getOrganizerId() {
		return organizerId;
	}
	/**
	 * @param organizerId the organizerId to set
	 */
	public void setOrganizerId(String organizerId) {
		this.organizerId = organizerId;
	}
	
	public boolean equals(Schedule s) {
		if(s == null) {
			return false;
		}
		boolean accum = true;
		accum &= s.getUuid().equals(this.uuid);
		accum &= s.getName().equals(this.name);
		accum &= s.getDuration() == this.duration;
		accum &= s.getStart_date().equals(this.start_date);
		accum &= s.getEnd_date().equals(this.end_date);
		accum &= s.getDaily_start_time().equals(this.daily_start_time);
		accum &= s.getDaily_end_time().equals(this.daily_end_time);
		if(s.getDeleted_at() != null && this.deleted_at != null) {
			accum &= s.getDeleted_at().equals(this.deleted_at);
		}
		else if (s.getDeleted_at() != null || this.deleted_at != null){
			accum = false;
		}
		if(s.getOrganizerId() != null && this.organizerId != null) {
			accum &= s.getOrganizerId().equals(this.organizerId);
		}
		else if (s.getOrganizerId() != null || this.organizerId != null){
			accum = false;
		}
		return accum;
	}
}
