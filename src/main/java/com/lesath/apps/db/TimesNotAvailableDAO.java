/**
 * 
 */
package com.lesath.apps.db;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import com.lesath.apps.model.Meeting;
import com.lesath.apps.model.TimeNotAvailable;

/**
 * @author Andy
 */
public class TimesNotAvailableDAO {
	java.sql.Connection conn;
	
	public TimesNotAvailableDAO() {
		try {
			conn = DatabaseUtil.connect();
		}
		catch(Exception e) {
			conn = null;
		}
	}
	
	public String addTimeNotAvailable(TimeNotAvailable t) throws Exception {
		try {
			String startString = t.getStart_time().toString().replaceAll("T", " ");
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM Scheduler.TimesNotAvailable WHERE schedule_id=\"" + t.getSchedule_id() + "\" AND start_time=\"" + startString + "\";";
			ResultSet resultSet = statement.executeQuery(query);
			if(resultSet.first()) {
				TimeNotAvailable oldTna = generateTimeNotAvailable(resultSet); 
				PreparedStatement ps = conn.prepareStatement("UPDATE Scheduler.TimesNotAvailable SET deleted_at=null WHERE uuid=\"" + oldTna.getUuid() + "\";");
				ps.executeUpdate();
				String currentTimeString = LocalDateTime.now().toString().replaceAll("T", " ");
				ps = conn.prepareStatement("UPDATE Scheduler.TimesNotAvailable SET created_at=\"" + currentTimeString + "\" WHERE uuid=\"" + oldTna.getUuid() + "\";");
				ps.executeUpdate();
				return oldTna.getUuid();
			}
			else {
				String uuid = UUID.randomUUID().toString();
				PreparedStatement ps;
		        ps = conn.prepareStatement("INSERT INTO TimesNotAvailable (schedule_id, uuid, start_time, "
		        		+ "created_at, deleted_at) values(?,?,?,?,?);");
		        ps.setString(1, t.getSchedule_id());
		        ps.setString(2, uuid);
		        ps.setString(3, t.getStart_time().toString());
		        ps.setString(4, LocalDateTime.now().toString().replaceAll("T", " "));
		        if(t.getDeleted_at() != null) {
		        	ps.setString(5, t.getDeleted_at().toString());
		        }
		        else {
		        	ps.setString(5,  null);
		        }
		        ps.execute();
		        return uuid;
			}
		} catch(Exception e) {
			throw new Exception("Could not add TimeNotAvailable: " + e.getMessage());
		}
	}
	
	public TimeNotAvailable getTimeNotAvailable(String uuid) throws Exception {
		TimeNotAvailable tna;
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM Scheduler.TimesNotAvailable WHERE uuid=\"" + uuid + "\" AND deleted_at IS null;";
			ResultSet resultSet = statement.executeQuery(query);
			if(resultSet.first()) {
				tna = generateTimeNotAvailable(resultSet);
			}
			else {
				tna = null;
			}
			resultSet.close();
            statement.close();
			return tna;
		} catch (Exception e) {
			throw new Exception("Failed to get TimeNotAvailable: " + e.getMessage());
		}
	}
	
	public ArrayList<TimeNotAvailable> getAllTimesNotAvailable() throws Exception {
		ArrayList<TimeNotAvailable> tnas = new ArrayList<TimeNotAvailable>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM Scheduler.TimesNotAvailable WHERE deleted_at IS null;";
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				tnas.add(generateTimeNotAvailable(resultSet));
			}
			resultSet.close();
            statement.close();
            if(tnas.isEmpty()) {
            	return null;
            }
            else {
            	return tnas;
            }
		} catch(Exception e) {
			throw new Exception("Failed to get TimesNotAvailable: " + e.getMessage());
		}
	}

	public ArrayList<TimeNotAvailable> getAllTimesNotAvailableForScheduleId(String scheduleId) throws Exception {
		ArrayList<TimeNotAvailable> tna = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM Scheduler.TimesNotAvailable WHERE schedule_id=\"" + scheduleId + "\" and deleted_at is null;";
	
			ResultSet rs = statement.executeQuery(query);
	
			while (rs.next()) {
				tna.add(generateTimeNotAvailable(rs));
			}
			rs.close();
			statement.close();
			if(tna.isEmpty()) {
				return null;
			}
			else {
				return tna;
			}
		} catch(Exception e) {
			throw new Exception("Failed to get all TimeNotAvailable: " + e.getMessage());
		}
	}
	
	public boolean deleteTimeNotAvailable(String schedule_id, LocalDateTime startTime) throws Exception {
		try {
			PreparedStatement ps;
			String currentTime = LocalDateTime.now().toString().replaceAll("T", " ");
            ps = conn.prepareStatement("UPDATE Scheduler.TimesNotAvailable SET deleted_at=\"" + currentTime + "\" WHERE schedule_id=\"" + schedule_id + "\" AND start_time=\"" + startTime.toString().replaceAll("T", " ") + "\";");
            int num = ps.executeUpdate();
            
            if (num == 1) {
            	return true;
            }
            else {
            	return false;
            }
		}
		catch(Exception e) {
			throw new Exception("Failed to delete TimeNotAvailable: " + e.getMessage());
		}
	}
	
	TimeNotAvailable generateTimeNotAvailable(ResultSet resultSet) throws Exception {
		try {
			String schedule_id = resultSet.getString("schedule_id");
			String id = resultSet.getString("uuid");
			LocalDateTime start_time = LocalDateTime.parse(resultSet.getString("start_time"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime created_at = LocalDateTime.parse(resultSet.getString("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime deleted_at = null;
			if(resultSet.getString("deleted_at") != null) {
				deleted_at = LocalDateTime.parse(resultSet.getString("deleted_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			}
			return new TimeNotAvailable(schedule_id, id, start_time, created_at, deleted_at);
		} catch(Exception e) {
			throw new Exception("Failed to generate TimeNotAvailable: " + e.getMessage());
		}
	}
}
