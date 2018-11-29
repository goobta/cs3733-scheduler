package com.lesath.apps.controller.model;

import java.time.LocalDateTime;

public class ScheduleConfig {
	String name;
	int meetingLength;
	LocalDateTime startDateTime;
	LocalDateTime endDateTime;
	
	public ScheduleConfig(String name, int meetingLength, LocalDateTime startDayTime, LocalDateTime endDayTime) {
		this.name = name;
		this.meetingLength = meetingLength;
		this.startDateTime = startDayTime;
		this.endDateTime = endDayTime;
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
	 * @return the meetingLength
	 */
	public int getMeetingLength() {
		return meetingLength;
	}

	/**
	 * @param meetingLength the meetingLength to set
	 */
	public void setMeetingLength(int meetingLength) {
		this.meetingLength = meetingLength;
	}

	/**
	 * @return the startDayTime
	 */
	public LocalDateTime getStartDayTime() {
		return startDateTime;
	}

	/**
	 * @param startDayTime the startDayTime to set
	 */
	public void setStartDayTime(LocalDateTime startDayTime) {
		this.startDateTime = startDayTime;
	}

	/**
	 * @return the endDayTime
	 */
	public LocalDateTime getEndDayTime() {
		return endDateTime;
	}

	/**
	 * @param endDayTime the endDayTime to set
	 */
	public void setEndDayTime(LocalDateTime endDayTime) {
		this.endDateTime = endDayTime;
	}
}
