/**
 * 
 */
package com.lesath.apps.db;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.lesath.apps.model.Meeting;
import com.lesath.apps.model.TimeNotAvailable;

/**
 * @author Andy
 */
public class TestTimesNotAvailableDAO {

	@Test
	public void testTimesNotAvailableDAO() {
		TimesNotAvailableDAO tnadao = new TimesNotAvailableDAO();
		LocalDateTime start = LocalDateTime.of(2018,12,04,8,0,0);
		LocalDateTime created = LocalDateTime.of(2018,12,04,10,0,0);
		TimeNotAvailable tna = new TimeNotAvailable("TestTimeNotAvailable",null,start,created,null);
		String uuid = null;
		try {
			uuid = tnadao.addTimeNotAvailable(tna);
			tna.setUuid(uuid);
			TimeNotAvailable gotTimeNotAvailable = tnadao.getTimeNotAvailable(uuid);
			ArrayList<TimeNotAvailable> gotTimesNotAvailable = tnadao.getAllTimesNotAvailable();
			assertTrue(gotTimeNotAvailable.equals(tna));
			boolean worked = false;
			for(TimeNotAvailable t: gotTimesNotAvailable) {
				worked |= t.equals(tna);
			}
			assertTrue(worked);
		} catch(Exception e) {
			System.out.println("TimesNotAvailableDAO test failed: " + e.getMessage());
			assertTrue(false);
		} finally {
			tnadao.deleteTimeNotAvailable(uuid);
		}
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
