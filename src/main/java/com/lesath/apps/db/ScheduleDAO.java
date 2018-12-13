package com.lesath.apps.db;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import com.lesath.apps.controller.sysAdminDaySearch.SysAdminDaySearchHandler;
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
     * @return returns the UUID of the schedule that was created 
     * @throws Exception given when fails to add the row in the table.
     */
    public String addSchedule(Schedule s) throws Exception {
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
            String temp = LocalDateTime.now().toString();
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
            return uuid;

        } catch (Exception e) {
            throw new Exception("Failed to add schedule: " + e.getMessage());
        }
    }
    
   /**
    * 
    * @return an ArrayList<Schedule> containing all the rows in the table schedules
    * @throws Exception when it fails to retrieve a row.
    */
   public ArrayList<Schedule> getAllSchedules() throws Exception {
        
    	ArrayList<Schedule> rowList = new ArrayList<Schedule>();
    	
    	try {
    		Statement statement = conn.createStatement();
            String query = "SELECT * FROM Schedules WHERE deleted_at IS null;";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Schedule sche = generateSchedule(resultSet);
                rowList.add(sche);
            }
            resultSet.close();
            statement.close();
            if(rowList.isEmpty()) {
            	return null;
            }
            else {
            	return rowList;
            }
        } catch (Exception e) {
            throw new Exception("Failed to get all schedules: " + e.getMessage());
        }
    }
   
   public Schedule getSchedule(String uuid) throws Exception {
	   try {
		   Schedule schedule;
		   Statement statement = conn.createStatement();
		   String query = "SELECT * FROM Schedules WHERE uuid =\"" + uuid + "\" AND deleted_at IS null;";
		   ResultSet resultSet = statement.executeQuery(query);
		   if(resultSet.first()) {
			   schedule = generateSchedule(resultSet);
		   }
		   else {
			   schedule = null;
		   }
		   resultSet.close();
		   statement.close();
		   return schedule;
	   } catch(Exception e) {
		   throw new Exception("Failed to get schedule: " + e.getMessage());
	   }
   }
   
   public boolean deleteSchedule(String uuid) throws Exception {
	   try {
		   PreparedStatement ps;
		   String currentTime = LocalDateTime.now().toString().replaceAll("T", " ");
		   ps = conn.prepareStatement("UPDATE Scheduler.Schedules SET deleted_at=\"" + currentTime + "\" WHERE uuid=\"" + uuid + "\";");
		   int numAffected = ps.executeUpdate();
		   return(numAffected == 1);
	   } catch(Exception e) {
		   throw new Exception("Failed to delete schedule: " + e.getMessage());
	   }
   }
   
   public boolean extendSchedule(String schedule_id, int delta) throws Exception {
	   try {
		   if(delta < 0) {
			   LocalDate start_date = getSchedule(schedule_id).getStart_date();
			   if(start_date.getDayOfWeek().name() == "MONDAY") {
				   delta = delta - 2;
			   }
			   
			   
			   
			   String startString = start_date.minusDays(-delta).toString().replaceAll("T", " ");
			   PreparedStatement ps = conn.prepareStatement("UPDATE Scheduler.Schedules SET start_date=\"" + startString + "\" WHERE uuid=\"" + schedule_id + "\";");
			   int numAffected = ps.executeUpdate();
			   return(numAffected == 1);
		   }
		   else if(delta > 0){
			   LocalDate end_date = getSchedule(schedule_id).getEnd_date();
			   if(end_date.getDayOfWeek().name() == "FRIDAY") {
				   delta = delta + 2;
			   }
			   
			   System.out.println("increase the end day");
			   String endString = end_date.plusDays(delta).toString().replaceAll("T", " ");
			   PreparedStatement ps = conn.prepareStatement("UPDATE Scheduler.Schedules SET end_date=\"" + endString + "\" WHERE uuid=\"" + schedule_id + "\";");
			   int numAffected = ps.executeUpdate();
			   return(numAffected == 1);
		   }
		   else {
			   return false;
		   }
	   } catch(Exception e) {
		   throw new Exception("Failed to extend schedule: " + e.getMessage());
	   }
   }
   
   public ArrayList<Schedule> getSchedulesDaysOld(int days) throws Exception{
	   try {
		   ArrayList<Schedule> allSchedules = getAllSchedules();
		   ArrayList<Schedule> schedules = new ArrayList<Schedule>();
		   LocalDateTime cutoff = LocalDateTime.now().minusDays(days);

		   if(allSchedules == null) {
		       return new ArrayList<Schedule>();
           }

		   for(Schedule s: allSchedules) {
			   if(s.getCreated_at().isBefore(cutoff)) {
				   schedules.add(s);
			   }
		   }
		   return schedules;
	   } catch(Exception e) {
	   		e.printStackTrace();
		   throw new Exception("Failed to get schedules days old: " + e.getMessage());
	   }
   }
   
   public ArrayList<Schedule> getSchedulesHoursOld(int hours) throws Exception{
	   try {
		   ArrayList<Schedule> allSchedules = getAllSchedules();
		   ArrayList<Schedule> schedules = new ArrayList<Schedule>();
		   LocalDateTime cutoff = LocalDateTime.now().minusHours(hours);
		   for(Schedule s: allSchedules) {
			   if(s.getCreated_at().isAfter(cutoff)) {
				   schedules.add(s);
			   }
		   }
		   return schedules;
	   } catch(Exception e) {
		   throw new Exception("Failed to get schedules hours old: " + e.getMessage());
	   }
   }
   
   public boolean deleteSchedules(ArrayList<String> uuids) throws Exception {
	   try {
		   boolean accum = true;
		   for(String uuid: uuids) {
			   accum &= deleteSchedule(uuid);
		   }
		   return accum;
	   } catch(Exception e) {
		   throw new Exception("Failed to delete schedules: " + e.getMessage());
	   }
   }
    
   /**
    * 
    * @param resultSet a row of the table schedules
    * @return A Schedule Object made from the resultSet
    * @throws Exception when cannot make the object.
    */
    private Schedule generateSchedule(ResultSet resultSet) throws Exception {
    	try {
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
    	} catch(Exception e) {
    		throw new Exception("Failed to generate schedule: " + e.getMessage());
    	}
    }
}
