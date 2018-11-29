package com.lesath.apps.controller.model;

import java.time.LocalDateTime;

public class ScheduleConfig {
	String name;
	int meetingLength;
	String startDayTime;
	String endDayTime;
	
	public ScheduleConfig(String name, int meetingLength, String startDayTime, String endDayTime) {
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
	public String getStartDayTime() {
		return startDayTime;
	}

	/**
	 * @param startDayTime the startDayTime to set
	 */
	public void setStartDayTime(String startDayTime) {
		this.startDayTime = startDayTime;
	}

	/**
	 * @return the endDayTime
	 */
	public String getEndDayTime() {
		return endDayTime;
	}

	/**
	 * @param endDayTime the endDayTime to set
	 */
	public void setEndDayTime(String endDayTime) {
		this.endDayTime = endDayTime;
	}
}
