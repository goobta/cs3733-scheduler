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
		String uuid = UUID.randomUUID().toString();
		try {
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
			String query = "SELECT * FROM Scheduler.Meetings WHERE uuid=\"" + uuid + "\";";
			ResultSet resultSet = statement.executeQuery(query);
			if(resultSet != null) {
				resultSet.first();
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
			
			if(resultSet != null) {
				while(resultSet.next()) {
					meetings.add(generateMeeting(resultSet));
				}
			}
			else {
				meetings = null;
			}
			resultSet.close();
            statement.close();
            return meetings;
		} catch(Exception e) {
			throw new Exception("Failed to get meetings: " + e.getMessage());
		}
	}

	public ArrayList<Meeting> getAllMeetingsForSchedule(String scheduleId) throws Exception {
		ArrayList<Meeting> meetings = new ArrayList<>();

		Statement statement = conn.createStatement();
		String query = "SELECT * FROM Scheduler.Meetings WHERE schedule_id=\"" + scheduleId + "\";";
		ResultSet resultSet = statement.executeQuery(query);

		if(resultSet != null) {
			while(resultSet.next()) {
				meetings.add(generateMeeting(resultSet));
			}

			resultSet.close();
		}
		statement.close();

		return meetings;
	}
	
	public boolean deleteMeeting(String uuid) {
		try {
			PreparedStatement ps;
			String currentTime = LocalDateTime.now().toString().replaceAll("T", " ");
            ps = conn.prepareStatement("UPDATE Scheduler.Meetings SET deleted_at=\"" + currentTime + "\" WHERE uuid=\"" + uuid + "\";");
            ps.execute();
			return true;
		}
		catch(Exception e) {
			return false;
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
