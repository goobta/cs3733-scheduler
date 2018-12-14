/**
 * 
 */
package com.lesath.apps.db;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import com.lesath.apps.model.Meeting;

/**
 * @author Andy
 */
public class MeetingDAO {
	java.sql.Connection conn;
	
	public MeetingDAO() {
		try {
			conn = DatabaseUtil.connect();
		}
		catch(Exception e) {
			conn = null;
		}
	}
	
	public String addMeeting(Meeting m) throws Exception {
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM Scheduler.Meetings WHERE deleted_at IS null AND schedule_id=\"" + m.getSchedule_id() + "\" AND start_time=\"" + m.getStart_time() + "\";";
			ResultSet existingSet = statement.executeQuery(query);
			boolean alreadyExists = existingSet.first();
			
			Statement statement2 = conn.createStatement();
			query = "SELECT * FROM Scheduler.TimesNotAvailable WHERE deleted_at is null AND schedule_id=\"" + m.getSchedule_id() + "\" AND start_time=\"" + m.getStart_time() + "\";";
			ResultSet notAvailableSet = statement2.executeQuery(query);
			boolean notAvailable = notAvailableSet.first();
			if(alreadyExists || notAvailable) {
				return null;
			}
			
			String uuid = UUID.randomUUID().toString();
			PreparedStatement ps;
            ps = conn.prepareStatement("INSERT INTO Meetings (schedule_id, uuid, start_time, created_at, "
            		+ "deleted_at, participant_name) "
            		+ "values(?,?,?,?,?,?);");
            ps.setString(1, m.getSchedule_id());
            ps.setString(2, uuid);
            ps.setString(3, m.getStart_time().toString());
            ps.setString(4, LocalDateTime.now().toString().replaceAll("T", " "));
            if(m.getDeleted_at() != null) {
            	ps.setString(5, m.getDeleted_at().toString().replaceAll("T", " "));
            } 
            else {
            	ps.setString(5,  null);
            }
            ps.setString(6, m.getParticipant_name());
            ps.execute();
            return uuid;
		}
		catch(Exception e) {
			throw new Exception("Failed to add meeting: " + e.getMessage());
		}
	}
	
	public Meeting getMeeting(String uuid) throws Exception {
		Meeting meeting;
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM Scheduler.Meetings WHERE uuid=\"" + uuid + "\" AND deleted_at IS null;";
			ResultSet resultSet = statement.executeQuery(query);
			if(resultSet.first()) {
				meeting = generateMeeting(resultSet);
			}
			else {
				meeting = null;
			}
			resultSet.close();
            statement.close();
			return meeting;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to get meeting: " + e.getMessage());
		}
	}
	
	public ArrayList<Meeting> getAllMeetings() throws Exception {
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM Scheduler.Meetings WHERE deleted_at IS null;";
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				meetings.add(generateMeeting(resultSet));
			}
			resultSet.close();
            statement.close();
            if(meetings.isEmpty()) {
            	return null;
            }
            else {
            	return meetings;
            }
		} catch(Exception e) {
			throw new Exception("Failed to get all meetings: " + e.getMessage());
		}
	}

	public ArrayList<Meeting> getAllMeetingsForSchedule(String scheduleId) throws Exception {
		ArrayList<Meeting> meetings = new ArrayList<>();
			try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM Scheduler.Meetings WHERE schedule_id=\"" + scheduleId + "\" and deleted_at IS null;";
			ResultSet resultSet = statement.executeQuery(query);
	
			while(resultSet.next()) {
				meetings.add(generateMeeting(resultSet));
			}
			resultSet.close();
			statement.close();
			if(meetings.isEmpty()) {
				return null;
			}
			else {
				return meetings;
			}
		} catch(Exception e) {
			throw new Exception("Failed to get all meetings for schedule: " + e.getMessage());
		}
	}
	
	public boolean deleteMeeting(String uuid) throws Exception {
		try {
			PreparedStatement ps;
			String currentTime = LocalDateTime.now().toString().replaceAll("T", " ");
            ps = conn.prepareStatement("UPDATE Scheduler.Meetings SET deleted_at=\"" + currentTime + "\" WHERE uuid=\"" + uuid + "\";");
            int numAffected = ps.executeUpdate();
            return(numAffected == 1);
		}
		catch(Exception e) {
			throw new Exception("Failed to delete meeting: " + e.getMessage());
		}
	}
	
	Meeting generateMeeting(ResultSet resultSet) throws Exception {
		try {
			String schedule_id = resultSet.getString("schedule_id");
			String id = resultSet.getString("uuid");
			LocalDateTime start_time = LocalDateTime.parse(resultSet.getString("start_time"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime created_at = LocalDateTime.parse(resultSet.getString("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime deleted_at = null;
			if(resultSet.getString("deleted_at") != null) {
				deleted_at = LocalDateTime.parse(resultSet.getString("deleted_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			}
			String participant_name = resultSet.getString("participant_name");
			return new Meeting(schedule_id, id, start_time, created_at, deleted_at, participant_name);
		} catch(Exception e) {
			throw new Exception("Failed to generate meeting: " + e.getMessage());
		}
	}
}
