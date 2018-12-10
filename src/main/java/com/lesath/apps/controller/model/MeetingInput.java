package com.lesath.apps.controller.model;

import java.time.LocalDateTime;

//This is different than the swagger name. It is named Meeting in swagger.
public class MeetingInput {

	//The uuid in the table
	String uuid;
	String participantName;
	LocalDateTime startDateTime;
	
	//You still have to grab meeting from the schedule table.
	
	public MeetingInput(String uuid,String participantName,LocalDateTime startDateTime) {
		this.uuid = uuid;
		this.participantName = participantName;
		this.startDateTime = startDateTime;		
	}
	
	
	
	/**
	 * @return the id
	 */
	public String getUuid() {
		return uuid;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String uuid) {
		this.uuid = uuid;
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
