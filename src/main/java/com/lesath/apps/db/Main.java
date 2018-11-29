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
		
		/*Connection conn ;
    	try  {
    		conn = DatabaseUtil.connect();
    		System.out.println("done");
    	} catch (Exception e) {
    		conn = null;
    		System.out.println("none");
    	}*/
    	
    	Schedule sche = new Schedule(null, "work", 15, LocalDate.now(), LocalDate.now(), LocalTime.now(), LocalTime.now(), LocalDateTime.now(), null );
    	
    	try {
    		ScheduleDAO s = new ScheduleDAO();
    		s.addSchedule(sche);
    		System.out.println("added");
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		System.out.println("fails to put");
    	}
    	
    	/*try {
    		ScheduleDAO s = new ScheduleDAO();
    		ArrayList<TestTable> lis = new ArrayList<TestTable>();
    		lis = s.getSchedule();
    		for (int i = 0; i < lis.size(); i++) {
    			System.out.print("x" + i + " : " + lis.get(i).getX() + " ");
    			System.out.println("y" + i + " : " + lis.get(i).getY());
    		}
    		
    	}
    	catch(Exception e){
    		System.out.println("fails to get");
    	}*/
    	
	}
}
