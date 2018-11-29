package com.lesath.apps.db;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import com.lesath.apps.model.Schedule;
import com.lesath.apps.model.TestTable;

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
    		ScheduleDAO s = new ScheduleDAO();
    		ArrayList<Schedule> lis = new ArrayList<Schedule>();
    		lis = s.getSchedule();
    		for (Schedule schee: lis) {
    			System.out.println(schee);
    		}
    		
    	}
    	catch(Exception e){
    		System.out.println("fails to get");
    	}
    	
	}
}
