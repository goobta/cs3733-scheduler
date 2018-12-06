/**
 * 
 */
package com.lesath.apps.db;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Assert;
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
			System.out.println("MeetingDAO test failed: " + e.getMessage());
			assertTrue(false);
		} finally {
			mdao.deleteMeeting(uuid);
		}
	}

	@Test
	public void testGetAllMeetingsForScheduleId() throws Exception {
		MeetingDAO mDAO = new MeetingDAO();

		String scheduleId = UUID.randomUUID().toString();

		LocalDateTime start1 = LocalDateTime.of(2018,12,04,8,0,0);
		LocalDateTime start2 = LocalDateTime.of(2018,12,05,8,0,0);
		LocalDateTime start3 = LocalDateTime.of(2018,12,06,8,0,0);
		LocalDateTime created = LocalDateTime.of(2018,12,04,10,0,0);

		Meeting meeting1 = new Meeting(scheduleId, null, start1, created, null, "TestParticiapnt");
		Meeting meeting2 = new Meeting(scheduleId, null, start2, created, null, "TestParticiapnt");
		Meeting meeting3 = new Meeting(scheduleId, null, start3, created, null, "TestParticiapnt");

		String meeting1UUID = mDAO.addMeeting(meeting1);
		String meeting2UUID = mDAO.addMeeting(meeting2);
		String meeting3UUID = mDAO.addMeeting(meeting3);

		ArrayList<Meeting> meetings = mDAO.getAllMeetingsForSchedule(scheduleId);

		Assert.assertEquals(3, meetings.size());
	}
}
