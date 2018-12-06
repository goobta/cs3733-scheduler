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
}
