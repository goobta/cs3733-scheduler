package com.lesath.apps.controller;

public class CreateScheduleResponse {
	int statusCode;
	String scheduleUuid;
	
	public CreateScheduleResponse(int code, String scheduleUUID) {
		statusCode = code;
		scheduleUuid = scheduleUUID;
	}
	
	public CreateScheduleResponse(int code) {
		statusCode = code;
		scheduleUuid = null;
	}
	
	public int getStatusCode() {
		return this.statusCode;
	}
	public void setStatusCode(int code) {
		this.statusCode = code;
	}
	public String getUuid() {
		return this.scheduleUuid;
	}
	public void setUuid(String UUID) {
		this.scheduleUuid = UUID;
	}
}
