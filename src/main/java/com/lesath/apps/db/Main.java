package com.lesath.apps.db;

import java.sql.Connection;



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
	}
}
