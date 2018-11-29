package com.lesath.apps.controller;

public class CreateScheduleResponse {
	int httpCode;
	String scheduleUuid;
	
	public CreateScheduleResponse(int code, String scheduleUUID) {
		httpCode = code;
		scheduleUuid = scheduleUUID;
	}
	
	public CreateScheduleResponse(int code) {
		httpCode = code;
		scheduleUuid = null;
	}
	
	public int getHTTPCode() {
		return this.httpCode;
	}
	public void setHttpCode(int code) {
		this.httpCode = code;
	}
	public String getUuid() {
		return this.scheduleUuid;
	}
	public void setUuid(String UUID) {
		this.scheduleUuid = UUID;
	}
}
