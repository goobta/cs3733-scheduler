/**
 * 
 */
package com.lesath.apps.db;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

import com.lesath.apps.model.Meeting;

/**
 * @author Andy
 *
 */
public class TestMeetingDAO {

	@Test
	public void testMeetingDAO() {
		MeetingDAO mdao = new MeetingDAO();
		LocalDateTime start = LocalDateTime.of(2018,12,04,8,0,0);
		LocalDateTime created = LocalDateTime.of(2018,12,04,10,0,0);
		Meeting meeting = new Meeting("TestMeeting",null,start,created,null,"TestName");
		String uuid = null;
		try {
			uuid = mdao.addMeeting(meeting);
			meeting.setUuid(uuid);
			Meeting gotMeeting = mdao.getMeeting(uuid);
			ArrayList<Meeting> gotMeetings = mdao.getAllMeetings();
			assertTrue(gotMeeting.equals(meeting));
			boolean worked = false;
			for(Meeting m: gotMeetings) {
				worked |= m.equals(meeting);
			}
			assertTrue(worked);
		} catch(Exception e) {
			System.out.println("MeetingDAO test failed");
			assertTrue(false);
		} finally {
			try {
				DatabaseUtil.connect().prepareStatement("DELETE FROM Meetings WHERE uuid=\"" + uuid + "\";").execute();
			} catch(Exception e) {
				System.out.println("Failed to reconnect to clean: " + e.getMessage());
			}
		}
	}
}
