package com.lesath.apps.controller.createMeeting;

import com.lesath.apps.controller.model.MeetingInput;
import com.lesath.apps.db.MeetingDAO;
import com.lesath.apps.model.Meeting;

public class CreateMeetingRequest{
	
	Meeting meet;
	
	
	public CreateMeetingRequest(MeetingInput inp, String scheduleId) {
		meet = new Meeting(scheduleId, null, inp.getStartDateTime(), null, null, inp.getParticipantName());
		
	}
	
	public String execute() {
		try {
			MeetingDAO mDao = new MeetingDAO();
			String uuid = mDao.addMeeting(meet);
			System.out.println("hgoifhgbklekighwrelighwre");
			return uuid;
		}
		catch (Exception e ) {
			System.out.println(e.getMessage());
    		e.printStackTrace();
    		System.out.println("fails to put");
    		return e.getMessage();
		}
	}
}
