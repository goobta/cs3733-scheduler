/**
 * 
 */
package com.lesath.apps.db;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

import com.lesath.apps.model.Meeting;
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
		TimeNotAvailable tna = new TimeNotAvailable("TestTimeNotAvailable",null,start,created,null);
		
		String uuid = tnadao.addTimeNotAvailable(tna);
		tna.setUuid(uuid);
		TimeNotAvailable gotTimeNotAvailable = tnadao.getTimeNotAvailable(uuid);
		assertTrue(gotTimeNotAvailable.equals(tna));
		
		ArrayList<TimeNotAvailable> gotTimesNotAvailable = tnadao.getAllTimesNotAvailable();
		boolean worked = false;
		for(TimeNotAvailable t: gotTimesNotAvailable) {
			worked |= t.equals(tna);
		}
		assertTrue(worked);
		
		assertTrue(tnadao.deleteTimeNotAvailable(uuid));
		gotTimeNotAvailable = tnadao.getTimeNotAvailable(uuid);
		assertNotNull(gotTimeNotAvailable.getDeleted_at());
		
		DatabaseUtil.connect().prepareStatement("DELETE FROM Scheduler.TimesNotAvailable WHERE uuid=\"" + uuid + "\";").execute();
	}
}
