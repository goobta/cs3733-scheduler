package com.lesath.apps.controller.model;

import java.time.LocalDateTime;

public class ToggleSlotClass {
	
	LocalDateTime startTime;
	boolean status;
	
	public ToggleSlotClass(LocalDateTime startTime,boolean status) {
		this.startTime = startTime;
		this.status = status;
	}

	/**
	 * @return the startTime
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

}
