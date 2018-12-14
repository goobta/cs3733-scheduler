/**
 * 
 */
package com.lesath.apps.db;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
		
		Schedule sched2 = new Schedule(null, "TestSchedule2", 30, start_date, end_date, daily_start_time, daily_end_time, created_at, null);
		Schedule sched3 = new Schedule(null, "TestSchedule3", 30, start_date, end_date, daily_start_time, daily_end_time, created_at, null);
		String uuid2 = sdao.addSchedule(sched2);
		String uuid3 = sdao.addSchedule(sched3);
		sched2.setUuid(uuid2);
		sched3.setUuid(uuid3);

		TimeUnit.SECONDS.sleep(1);

		ArrayList<Schedule> scheds = sdao.getSchedulesDaysOld(0);
		int got = 0;
		assertFalse(scheds.isEmpty());
		for(Schedule s: scheds) {
			if(s.equals(sched) || s.equals(sched2) || s.equals(sched3)) {
				got++;
			}
		}
		assertFalse(got==0);
		scheds = sdao.getSchedulesHoursOld(2);
		got = 0;
		assertFalse(scheds.isEmpty());
		for(Schedule s: scheds) {
			if(s.equals(sched) || s.equals(sched2) || s.equals(sched3)) {
				got++;
			}
		}
		assertTrue(got==3);
		
		assertTrue(sdao.extendSchedule(uuid, -1));
		assertTrue(sdao.getSchedule(uuid).getStart_date().plusDays(1).equals(sched.getStart_date()));
		assertTrue(sdao.extendSchedule(uuid, 1));
		assertTrue(sdao.getSchedule(uuid).getEnd_date().minusDays(1).equals(sched.getEnd_date()));
		
		
		assertTrue(sdao.deleteSchedule(uuid));
		assertNull(sdao.getSchedule(uuid));
		assertNull(sdao.getSchedule("NotA_UUID"));
		ArrayList<String> uuids = new ArrayList<String>();
		uuids.add(uuid2);
		uuids.add(uuid3);
		assertTrue(sdao.deleteSchedules(uuids));
		assertNull(sdao.getSchedule(uuid2));
		assertNull(sdao.getSchedule(uuid3));
		
		DatabaseUtil.connect().prepareStatement("DELETE FROM Schedules WHERE uuid=\"" + uuid + "\";").execute();
		DatabaseUtil.connect().prepareStatement("DELETE FROM Schedules WHERE uuid=\"" + uuid2 + "\";").execute();
		DatabaseUtil.connect().prepareStatement("DELETE FROM Schedules WHERE uuid=\"" + uuid3 + "\";").execute();
	}
}
