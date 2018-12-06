package com.lesath.apps.controller.toggleTimeSlot;

import java.time.LocalDateTime;

import com.lesath.apps.controller.model.MeetingInput;
import com.lesath.apps.db.TimesNotAvailableDAO;
import com.lesath.apps.model.Meeting;
import com.lesath.apps.model.TimeNotAvailable;

public class ToggleTimeSlotRequest {

	TimeNotAvailable t;
	boolean typeOfToggle;
	
	public ToggleTimeSlotRequest(String scheduleId, LocalDateTime start_time, boolean typeOfToggle ) {
		t = new TimeNotAvailable(scheduleId,null, start_time, null, null );
		this.typeOfToggle = typeOfToggle;
		
	}
	
	public boolean execute() {
		
		try {
		if(!typeOfToggle) {
			TimesNotAvailableDAO tDao = new TimesNotAvailableDAO();
			String uuid = tDao.addTimeNotAvailable(t);
		}
		return true;
		}
		catch (Exception e) {
			return false;
		}
	}
}
