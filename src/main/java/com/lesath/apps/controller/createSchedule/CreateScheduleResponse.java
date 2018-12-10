package com.lesath.apps.controller.createSchedule;

public class CreateScheduleResponse {
	String scheduleUuid;
	
	public CreateScheduleResponse(String uuid) {
		scheduleUuid = uuid;
	}
	
	public String getUuid() {
		return this.scheduleUuid;
	}
	public void setUuid(String UUID) {
		this.scheduleUuid = UUID;
	}
}
