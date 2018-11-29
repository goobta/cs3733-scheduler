package com.lesath.apps.controller.model;

import java.time.LocalDateTime;

public class ScheduleConfig {
	String name;
	int meetingLength;
	LocalDateTime startDayTime;
	LocalDateTime endDayTime;
	
	public ScheduleConfig(String name, int meetingLength, LocalDateTime startDayTime, LocalDateTime endDayTime) {
		this.name = name;
		this.meetingLength = meetingLength;
		this.startDayTime = startDayTime;
		this.endDayTime = endDayTime;
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
		return startDayTime;
	}

	/**
	 * @param startDayTime the startDayTime to set
	 */
	public void setStartDayTime(LocalDateTime startDayTime) {
		this.startDayTime = startDayTime;
	}

	/**
	 * @return the endDayTime
	 */
	public LocalDateTime getEndDayTime() {
		return endDayTime;
	}

	/**
	 * @param endDayTime the endDayTime to set
	 */
	public void setEndDayTime(LocalDateTime endDayTime) {
		this.endDayTime = endDayTime;
	}
}
