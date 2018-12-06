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
	public void testMeetingDAO() throws Exception{
		MeetingDAO mdao = new MeetingDAO();
		LocalDateTime start = LocalDateTime.of(2018,12,04,8,0,0);
		LocalDateTime created = LocalDateTime.of(2018,12,04,10,0,0);
		Meeting meeting = new Meeting("TestMeeting",null,start,created,null,"TestName");
		
		String uuid = mdao.addMeeting(meeting);
		meeting.setUuid(uuid);
		Meeting gotMeeting = mdao.getMeeting(uuid);
		assertTrue(gotMeeting.equals(meeting));
		
		ArrayList<Meeting> gotMeetings = mdao.getAllMeetings();
		boolean worked = false;
		for(Meeting m: gotMeetings) {
			worked |= m.equals(meeting);
		}
		assertTrue(worked);

		assertTrue(mdao.deleteMeeting(uuid));
		gotMeeting = mdao.getMeeting(uuid);
		assertNotNull(gotMeeting.getDeleted_at());
		
		DatabaseUtil.connect().prepareStatement("DELETE FROM Scheduler.Meetings WHERE uuid=\"" + uuid + "\";").execute();
	}
}
