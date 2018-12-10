/**
 * 
 */
package com.lesath.apps.db;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.Test;

import com.lesath.apps.model.Schedule;

/**
 * @author Andy
 *
 */
public class TestScheduleDAO {

	@Test
	public void testScheduleDAO() throws Exception{
		ScheduleDAO sdao = new ScheduleDAO();
		LocalDate d1 = LocalDate.of(2018,12,04);
		LocalDate d2 = LocalDate.of(2018,12,05);
		LocalTime t1 = LocalTime.of(8, 0);
		LocalTime t2 = LocalTime.of(11, 0);
		LocalDateTime created = LocalDateTime.of(2018, 6, 7, 7, 0);
		Schedule sched = new Schedule(null, "TestSchedule", 30, d1, d2, t1, t2, created, null);
		
		String uuid = sdao.addSchedule(sched);
		sched.setUuid(uuid);
		Schedule gotSchedule = sdao.getSchedule(uuid);
		assertTrue(gotSchedule.equals(sched));
		
		ArrayList<Schedule> gotSchedules = sdao.getAllSchedules();
		boolean worked = false;
		for(Schedule s: gotSchedules) {
			worked |= s.equals(sched);
			assertNull(s.getDeleted_at());
		}
		assertTrue(worked);
		
		assertTrue(sdao.extendSchedule(uuid, -1));
		assertTrue(sdao.getSchedule(uuid).getStart_date().plusDays(1).equals(sched.getStart_date()));
		
		assertTrue(sdao.extendSchedule(uuid, 1));
		assertTrue(sdao.getSchedule(uuid).getEnd_date().minusDays(1).equals(sched.getEnd_date()));
		
		assertTrue(sdao.deleteSchedule(uuid));
		gotSchedule = sdao.getSchedule(uuid);
		assertNotNull(gotSchedule.getDeleted_at());

		DatabaseUtil.connect().prepareStatement("DELETE FROM Schedules WHERE uuid=\"" + uuid + "\";").execute();
	}
}
