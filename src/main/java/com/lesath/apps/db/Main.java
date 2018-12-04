package com.lesath.apps.db;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import com.lesath.apps.model.Meeting;
import com.lesath.apps.model.Schedule;
import com.lesath.apps.model.TestTable;
import com.lesath.apps.model.TimeNotAvailable;

/**
 * 
 * @author revant
 *	This class is to test out dao objects. Please don't write usefull code here.
 */
public class Main {

	public static void main(String args[]) {
		

    	
    	Schedule sche = new Schedule(null, "work", 15, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), LocalDateTime.now(), null );
    	/*
    	try {
    		ScheduleDAO s = new ScheduleDAO();
    		s.addSchedule(sche);
    		System.out.println("added");
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		System.out.println("fails to put");
    	}*/
    	
    	try {
    		//ScheduleDAO s = new ScheduleDAO();
    		//ArrayList<Schedule> schedules = new ArrayList<Schedule>();
    		//schedules = s.getSchedule();
    		
    		MeetingDAO mdao = new MeetingDAO();
    		mdao.addMeeting(new Meeting("a","b",LocalDateTime.now(), LocalDateTime.now(), null, "JimJim"));
    		//Meeting m = mdao.getMeeting("91194d6f-dafb-47f2-809c-38029d0a5df3");
    		//System.out.println(m.getParticipant_name() + "'s meeting");
    		System.out.println(mdao.getAllMeetings().get(0).getParticipant_name());
    		//for (Schedule schee: schedules) {
    			//System.out.println(schee);
    		//}
    		
    		TimesNotAvailableDAO tnadao = new TimesNotAvailableDAO();
    		tnadao.addTimeNotAvailable(new TimeNotAvailable("JimTime2",null,LocalDateTime.now(),LocalDateTime.now(),null));
    		//System.out.println(tnadao.getTimeNotAvailable("35333e0d-23b3-4f04-a13c-8058caa8d998").getSchedule_id());
    		System.out.println(tnadao.getAllTimesNotAvailable().get(0).getSchedule_id());
    	}
    	catch(Exception e){
    		//e.printStackTrace();
    		System.out.println("fails to get");
    	}
    	
	}
}
