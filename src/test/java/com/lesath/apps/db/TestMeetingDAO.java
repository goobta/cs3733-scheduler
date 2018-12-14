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
	public void testMeetingDAO() throws Exception{
		MeetingDAO mdao = new MeetingDAO();
		LocalDateTime start = LocalDateTime.of(2018,12,04,8,0,0);
		LocalDateTime created = LocalDateTime.of(2018,12,04,10,0,0);
		Meeting meeting = new Meeting(UUID.randomUUID().toString(),null,start,created,null,"TestName");
		
		String uuid = mdao.addMeeting(meeting);
		meeting.setUuid(uuid);
		assertNull(mdao.addMeeting(meeting));
		Meeting gotMeeting = mdao.getMeeting(uuid);
		assertTrue(gotMeeting.equals(meeting));
		
		ArrayList<Meeting> gotMeetings = mdao.getAllMeetings();
		boolean worked = false;
		for(Meeting m: gotMeetings) {
			worked |= m.equals(meeting);
			assertNull(m.getDeleted_at());
		}
		assertTrue(worked);
		
		gotMeetings = mdao.getAllMeetingsForSchedule(meeting.getSchedule_id());
		worked = false;
		for(Meeting m: gotMeetings) {
			worked |= m.equals(meeting);
			assertNull(m.getDeleted_at());
		}
		assertTrue(worked);

		assertTrue(mdao.deleteMeeting(uuid));
		assertFalse(mdao.deleteMeeting("NotA_UUID"));
		assertNull(mdao.getMeeting(uuid));
		assertNull(mdao.getAllMeetingsForSchedule(meeting.getSchedule_id()));
		
		DatabaseUtil.connect().prepareStatement("DELETE FROM Scheduler.Meetings WHERE uuid=\"" + uuid + "\";").execute();
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
