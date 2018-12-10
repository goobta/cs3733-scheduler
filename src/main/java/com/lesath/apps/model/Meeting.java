package com.lesath.apps.model;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;

public class Meeting {
	String schedule_id;

	@SerializedName("id")
	String uuid;

	@SerializedName("startTime")
	LocalDateTime start_time;
	LocalDateTime created_at;
	LocalDateTime deleted_at;

	@SerializedName("participantName")
	String participant_name;
	
	/**
	 * @param schedule_id
	 * @param uuid
	 * @param start_time
	 * @param created_at
	 * @param deleted_at
	 * @param participant_name
	 */
	public Meeting(String schedule_id, String uuid, LocalDateTime start_time, LocalDateTime created_at,
			LocalDateTime deleted_at, String participant_name) {
		super();
		this.schedule_id = schedule_id;
		this.uuid = uuid;
		this.start_time = start_time;
		this.created_at = created_at;
		this.deleted_at = deleted_at;
		this.participant_name = participant_name;
	}
	
	/**
	 * @return the schedule_id
	 */
	public String getSchedule_id() {
		return schedule_id;
	}
	/**
	 * @param schedule_id the schedule_id to set
	 */
	public void setSchedule_id(String schedule_id) {
		this.schedule_id = schedule_id;
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
	 * @return the start_time
	 */
	public LocalDateTime getStart_time() {
		return start_time;
	}
	/**
	 * @param start_time the start_time to set
	 */
	public void setStart_time(LocalDateTime start_time) {
		this.start_time = start_time;
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
	 * @return the participant_name
	 */
	public String getParticipant_name() {
		return participant_name;
	}
	/**
	 * @param participant_name the participant_name to set
	 */
	public void setParticipant_name(String participant_name) {
		this.participant_name = participant_name;
	}
	
	public boolean equals(Meeting m) {
		if(m == null) {
			return false;
		}
		boolean accum = true;
		accum &= m.getSchedule_id().equals(this.schedule_id);
		accum &= m.getUuid().equals(this.uuid);
		accum &= m.getStart_time().equals(this.start_time);
		if(m.getDeleted_at() != null && this.deleted_at != null) {
			accum &= m.getDeleted_at().equals(this.deleted_at);
		}
		else if (m.getDeleted_at() != null || this.deleted_at != null){
			accum = false;
		}
		accum &= m.getParticipant_name().equals(this.participant_name);
		return accum;
	}
}
