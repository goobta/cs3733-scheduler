package com.lesath.apps.controller;

public class CreateScheduleResponse {
	int httpCode;
	
	public CreateScheduleResponse(int code) {
		httpCode = code;
	}
	
	public int getHTTPCode() {
		return this.httpCode;
	}
	public void setHttpCode(int code) {
		this.httpCode = code;
	}
}
