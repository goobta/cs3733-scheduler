package com.lesath.apps.controller.createMeeting;

public class CreateMeetingResponse {
	String meetingUuid;
	
	public CreateMeetingResponse(String uuid) {
		meetingUuid = uuid;
	}
	
	public String getUuid() {
		return this.meetingUuid;
	}
	public void setUuid(String UUID) {
		this.meetingUuid = UUID;
	}
}
