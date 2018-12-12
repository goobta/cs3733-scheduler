package com.lesath.apps.controller.extendSchedule;

import com.lesath.apps.controller.model.ExtendSchedule;
import com.lesath.apps.db.ScheduleDAO;

public class ExtendScheduleRequest {

	String scheduleId;
	ExtendSchedule ex;
	
	ExtendScheduleRequest(String scheduleId, ExtendSchedule ex){
		this.scheduleId = scheduleId;
		this.ex = ex;
	}
	
	public boolean execute() {
		
		ScheduleDAO sDao = new ScheduleDAO();
		try {
			
			
		}catch (Exception e) {
			
		}
		
		return false;
	}
}
