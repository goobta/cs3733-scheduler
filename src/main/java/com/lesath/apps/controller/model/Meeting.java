package com.lesath.apps.controller.model;

import java.time.LocalDateTime;

public class Meeting {

	//The uuid in the table
	String id;
	String participantName;
	LocalDateTime startDateTime;
	
	//You still have to grab meeting from the schedule table.
	
	public Meeting(String id,String participantName,LocalDateTime startDateTime) {
		this.id = id;
		this.participantName = participantName;
		this.startDateTime = startDateTime;		
	}
	
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the participantName
	 */
	public String getParticipantName() {
		return participantName;
	}
	/**
	 * @param participantName the participantName to set
	 */
	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}
	/**
	 * @return the startDateTime
	 */
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}
	/**
	 * @param startDateTime the startDateTime to set
	 */
	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}
	

}
