package com.lesath.apps.controller;

import java.time.LocalDateTime;

import com.lesath.apps.controller.model.ScheduleConfig;
import com.lesath.apps.db.ScheduleDAO;
import com.lesath.apps.model.Schedule;

public class CreateScheduleRequest {
	Schedule schedule;
	
	public CreateScheduleRequest(ScheduleConfig sc) {
		schedule = new Schedule(
				null,
				sc.getName(),
				sc.getMeetingLength(),
				sc.getStartDayTime().toLocalDate(),
				sc.getEndDayTime().toLocalDate(),
				sc.getStartDayTime().toLocalTime(),
				sc.getEndDayTime().toLocalTime(),
				LocalDateTime.now(),
				null);
	}
	
	public String execute() {
		try {
    		ScheduleDAO s = new ScheduleDAO();
    		
    		return s.addSchedule(schedule);
       	}
    	catch(Exception e){
    		e.printStackTrace();
    		System.out.println("fails to put");
    		
    		return "";
    	}
	}
}
