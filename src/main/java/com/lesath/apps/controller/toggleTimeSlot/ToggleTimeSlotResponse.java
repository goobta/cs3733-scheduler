package com.lesath.apps.controller.toggleTimeSlot;

public class ToggleTimeSlotResponse {

	boolean status;
	
	ToggleTimeSlotResponse(boolean status){
		this.status = status;
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
