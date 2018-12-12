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

import com.lesath.apps.model.TimeNotAvailable;

/**
 * @author Andy
 */
public class TestTimesNotAvailableDAO {

	@Test
	public void testTimesNotAvailableDAO() throws Exception {
		TimesNotAvailableDAO tnadao = new TimesNotAvailableDAO();
		LocalDateTime start = LocalDateTime.of(2018,12,04,8,0,0);
		LocalDateTime created = LocalDateTime.of(2018,12,04,10,0,0);
		TimeNotAvailable tna = new TimeNotAvailable(UUID.randomUUID().toString(),null,start,created,null);
		
		String uuid = tnadao.addTimeNotAvailable(tna);
		tna.setUuid(uuid);
		TimeNotAvailable gotTimeNotAvailable = tnadao.getTimeNotAvailable(uuid);
		assertTrue(gotTimeNotAvailable.equals(tna));
		
		ArrayList<TimeNotAvailable> gotTimesNotAvailable = tnadao.getAllTimesNotAvailable();
		boolean worked = false;
		for(TimeNotAvailable t: gotTimesNotAvailable) {
			worked |= t.equals(tna);
			assertNull(t.getDeleted_at());
		}
		assertTrue(worked);
		
		gotTimesNotAvailable = tnadao.getAllTimesNotAvailableForScheduleId(tna.getSchedule_id());
		worked = false;
		for(TimeNotAvailable t: gotTimesNotAvailable) {
			worked |= t.equals(tna);
			assertNull(t.getDeleted_at());
		}
		assertTrue(worked);
		
		assertTrue(tnadao.deleteTimeNotAvailable(tna.getSchedule_id(), tna.getStart_time()));
		assertFalse(tnadao.deleteTimeNotAvailable("BadScheduleId",tna.getStart_time()));
		assertNull(tnadao.getTimeNotAvailable(uuid));
		assertNull(tnadao.getAllTimesNotAvailableForScheduleId(tna.getSchedule_id()));
		
		TimeNotAvailable newTna = tna;
		newTna.setDeleted_at(null);
		assertTrue(tnadao.addTimeNotAvailable(newTna).equals(tna.getUuid()));
		TimeNotAvailable newGotTna = tnadao.getTimeNotAvailable(newTna.getUuid());
		//assertFalse(gotTimeNotAvailable.getCreated_at().equals(newGotTna.getCreated_at()));
		assertNull(newGotTna.getDeleted_at());
		
		DatabaseUtil.connect().prepareStatement("DELETE FROM Scheduler.TimesNotAvailable WHERE uuid=\"" + uuid + "\";").execute();
	}

	@Test
	public void testGetTimesNotAvailable() throws Exception {
		TimesNotAvailableDAO tnaDAO = new TimesNotAvailableDAO();

		String scheduleId = UUID.randomUUID().toString();

		LocalDateTime start1 = LocalDateTime.of(2018,12,04,8,0,0);
		LocalDateTime start2 = LocalDateTime.of(2018,12,05,8,0,0);
		LocalDateTime start3 = LocalDateTime.of(2018,12,06,8,0,0);

		LocalDateTime created = LocalDateTime.of(2018,12,04,10,0,0);

		TimeNotAvailable tna1 = new TimeNotAvailable(scheduleId, null, start1, created, null);
		TimeNotAvailable tna2 = new TimeNotAvailable(scheduleId, null, start2, created, null);
		TimeNotAvailable tna3 = new TimeNotAvailable(scheduleId, null, start3, created, null);

		String tna1UUID = tnaDAO.addTimeNotAvailable(tna1);
		String tna2UUID = tnaDAO.addTimeNotAvailable(tna2);
		String tna3UUID = tnaDAO.addTimeNotAvailable(tna3);

		ArrayList<TimeNotAvailable> tnas = tnaDAO.getAllTimesNotAvailableForScheduleId(scheduleId);

		Assert.assertEquals(3, tnas.size());
	}
}
