package com.lesath.apps.db;

import java.sql.Connection;
import java.util.ArrayList;

import com.lesath.apps.model.TestTable;


public class Main {

	public static void main(String args[]) {
		
		Connection conn ;
    	try  {
    		conn = DatabaseUtil.connect();
    		System.out.println("done");
    	} catch (Exception e) {
    		conn = null;
    		System.out.println("none");
    	}
    	
    	TestTable t = new TestTable(5,7);
    	
    	/*try {
    		ScheduleDAO s = new ScheduleDAO();
    		s.addSchedule(t);
    		System.out.println("added");
    	}
    	catch(Exception e){
    		System.out.println("fails to put");
    	}*/
    	
    	try {
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
    	}
    	
	}
}
