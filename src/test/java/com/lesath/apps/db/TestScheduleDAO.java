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
		LocalDate start_date = LocalDate.of(2018,12,04);
		LocalDate end_date = LocalDate.of(2018,12,05);
		LocalTime daily_start_time = LocalTime.of(8, 0);
		LocalTime daily_end_time = LocalTime.of(11, 0);
		LocalDateTime created_at = LocalDateTime.of(2018, 6, 7, 7, 0);
		Schedule sched = new Schedule(null, "TestSchedule", 30, start_date, end_date, daily_start_time, daily_end_time, created_at, null);
		
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
		
		assertTrue(sdao.deleteSchedule(uuid));
		assertNull(sdao.getSchedule(uuid));
		assertNull(sdao.getSchedule("NotA_UUID"));

		DatabaseUtil.connect().prepareStatement("DELETE FROM Schedules WHERE uuid=\"" + uuid + "\";").execute();
	}
}
