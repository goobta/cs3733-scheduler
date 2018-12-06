package com.lesath.apps.controller.toggleTimeSlot;

import java.time.LocalDateTime;

import com.lesath.apps.controller.model.MeetingInput;
import com.lesath.apps.controller.model.ToggleSlotClass;
import com.lesath.apps.db.TimesNotAvailableDAO;
import com.lesath.apps.model.Meeting;
import com.lesath.apps.model.TimeNotAvailable;

public class ToggleTimeSlotRequest {

	TimeNotAvailable t;
	boolean typeOfToggle;
	
	public ToggleTimeSlotRequest(String scheduleId, ToggleSlotClass info ) {
		t = new TimeNotAvailable(scheduleId,null, info.getStartTime(), null, null );
		this.typeOfToggle = info.isStatus();
		
	}
	
	public boolean execute() {
		
		try {
			TimesNotAvailableDAO tDao = new TimesNotAvailableDAO();
				if(!typeOfToggle) {
					
					String uuid = tDao.addTimeNotAvailable(t);
				}
				else {
					
				}
				return true;
		}
		catch (Exception e) {
			return false;
		}
	}
}
