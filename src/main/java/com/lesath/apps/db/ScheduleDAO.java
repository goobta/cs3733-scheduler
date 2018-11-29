package com.lesath.apps.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import com.lesath.apps.model.Schedule;



public class ScheduleDAO {

	java.sql.Connection conn;

    public ScheduleDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    /**
     * 
     * @param s - Schedule object to be added as a row in table Schedule
     * @return returns true when method is able to add the tuple. 
     * @throws Exception given when fails to add the row in the table.
     */
    public boolean addSchedule(Schedule s) throws Exception {
    	String uuid = UUID.randomUUID().toString();
        try {
            PreparedStatement ps;
            ps = conn.prepareStatement("INSERT INTO Schedules (uuid, name, duration, start_date, "
            		+ "end_date, daily_start_time, daily_end_time, created_at, deleted_at) "
            		+ "values(?,?,?,?,?,?,?,?,?);");
            ps.setString(1, uuid);
            ps.setString(2, s.getName());
            ps.setInt(3, s.getDuration());
            ps.setString(4, s.getStart_date().toString());
            ps.setString(5, s.getEnd_date().toString());
            ps.setString(6, s.getDaily_start_time().toString());
            ps.setString(7, s.getDaily_end_time().toString());
            String temp = s.getCreated_at().toString();
            temp.replaceAll("T", " ");
            ps.setString(8, temp);
            if(s.getDeleted_at() != null) {
            temp = s.getDeleted_at().toString();
            temp.replaceAll("T", " ");
            ps.setString(9, temp);
            }
            else {
            	ps.setString(9, null);
            }
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert constant: " + e.getMessage());
        }
    }
    
   /* public ArrayList<TestTable> getSchedule() throws Exception {
        
    	ArrayList<TestTable> rowList = new ArrayList<TestTable>();
    	
    	try {
    		Statement statement = conn.createStatement();
            String query = "SELECT * FROM testTable";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                TestTable row = generateConstant(resultSet);
                rowList.add(row);
            }
            resultSet.close();
            statement.close();
            return rowList;

        } catch (Exception e) {
        	rowList = null;
            throw new Exception("Failed to insert constant: " + e.getMessage());
        }
    }
    
    private TestTable generateConstant(ResultSet resultSet) throws Exception {
        int x  = resultSet.getInt("x");
        int y = resultSet.getInt("y");
        return new TestTable(x,y);
    }*/
}
