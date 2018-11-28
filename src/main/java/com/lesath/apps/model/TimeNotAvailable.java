
package com.lesath.apps.model;

import java.time.LocalDateTime;

public class TimeNotAvailable {
	String schedule_id;
	String uuid;
	LocalDateTime start_time;
	LocalDateTime created_at;
	LocalDateTime deleted_at;
	/**
	 * @param schedule_id
	 * @param uuid
	 * @param start_time
	 * @param created_at
	 * @param deleted_at
	 */
	public TimeNotAvailable(String schedule_id, String uuid, LocalDateTime start_time, LocalDateTime created_at,
			LocalDateTime deleted_at) {
		super();
		this.schedule_id = schedule_id;
		this.uuid = uuid;
		this.start_time = start_time;
		this.created_at = created_at;
		this.deleted_at = deleted_at;
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
	
}
