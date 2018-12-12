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
			boolean boo = sDao.extendSchedule(scheduleId, ex.getDeltaDays());
			return boo;
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		
	}
}
