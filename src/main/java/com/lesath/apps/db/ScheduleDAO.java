package com.lesath.apps.db;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    
   /**
    * 
    * @return an ArrayList<Schedule> containing all the rows in the table schedules
    * @throws Exception when it fails to retrieve a row.
    */
   public ArrayList<Schedule> getSchedule() throws Exception {
        
    	ArrayList<Schedule> rowList = new ArrayList<Schedule>();
    	
    	try {
    		Statement statement = conn.createStatement();
            String query = "SELECT * FROM Schedules";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Schedule sche = generateSchedule(resultSet);
                rowList.add(sche);
            }
            resultSet.close();
            statement.close();
            return rowList;

        } catch (Exception e) {
        	rowList = null;
            throw new Exception("Failed to get schedule: " + e.getMessage());
        }
    }
    
   /**
    * 
    * @param resultSet a row of the table schedules
    * @return A Schedule Object made from the resultSet
    * @throws Exception when cannot make the object.
    */
    private Schedule generateSchedule(ResultSet resultSet) throws Exception {
    	
    	LocalDateTime deleted_at;
        String uuid = resultSet.getString("uuid");
        String name = resultSet.getString("name");
        int duration = resultSet.getInt("duration");
        LocalDate start_date = LocalDate.parse(resultSet.getString("start_date"));
        LocalDate end_date = LocalDate.parse(resultSet.getString("end_date"));
        LocalTime daily_start_time = LocalTime.parse(resultSet.getString("daily_start_time") );
        LocalTime daily_end_time = LocalTime.parse((resultSet.getString("daily_end_time")));
        LocalDateTime created_at = LocalDateTime.parse(resultSet.getString("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if(resultSet.getString("deleted_at") != null) {
        	deleted_at = LocalDateTime.parse(resultSet.getString("deleted_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        else {
        	deleted_at = null;
        }
        return new Schedule(uuid, name, duration, start_date, end_date, daily_start_time, daily_end_time, created_at, deleted_at) ;
    }
}
