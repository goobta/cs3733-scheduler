/**
 * 
 */
package com.lesath.apps.controller.getSchedule;

import com.amazonaws.services.lambda.runtime.Context;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.LambdaResponse;
import com.lesath.apps.controller.TestAPIGatewayRequest;
import com.lesath.apps.db.MeetingDAO;
import com.lesath.apps.db.ScheduleDAO;
import com.lesath.apps.db.TimesNotAvailableDAO;
import com.lesath.apps.model.Meeting;
import com.lesath.apps.model.Schedule;
import com.lesath.apps.model.TimeNotAvailable;
import com.lesath.apps.util.HTTPMethod;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Andy
 */
public class TestGetSchedule {

	@Test
	public void endToEndTestSchedule() throws Exception {
		ScheduleDAO sDAO = new ScheduleDAO();

		LocalDate d1 = LocalDate.of(2018,12,04);
		LocalDate d2 = LocalDate.of(2018,12,05);
		LocalTime t1 = LocalTime.of(8, 0);
		LocalTime t2 = LocalTime.of(11, 0);
		LocalDateTime created = LocalDateTime.of(2018, 6, 7, 7, 0);
		Schedule sched = new Schedule(null, "TestSchedule", 30, d1, d2, t1, t2, created, null, "388b1cac-f3fd-42d0-9c58-92c72904f4b1");

		String uuid = sDAO.addSchedule(sched);

		MeetingDAO mDAO = new MeetingDAO();

		LocalDateTime start1 = LocalDateTime.of(2018,12,04,8,0,0);
		LocalDateTime start2 = LocalDateTime.of(2018,12,05,8,0,0);
		LocalDateTime start3 = LocalDateTime.of(2018,12,06,8,0,0);
		LocalDateTime createdM = LocalDateTime.of(2018,12,04,10,0,0);

		Meeting meeting1 = new Meeting(uuid, null, start1, createdM, null, "TestParticiapnt");
		Meeting meeting2 = new Meeting(uuid, null, start2, createdM, null, "TestParticiapnt");
		Meeting meeting3 = new Meeting(uuid, null, start3, createdM, null, "TestParticiapnt");

		String meeting1UUID = mDAO.addMeeting(meeting1);
		String meeting2UUID = mDAO.addMeeting(meeting2);
		String meeting3UUID = mDAO.addMeeting(meeting3);


		TimesNotAvailableDAO tnaDAO = new TimesNotAvailableDAO();

		LocalDateTime startT1 = LocalDateTime.of(2018,12,04,8,0,0);
		LocalDateTime startT2 = LocalDateTime.of(2018,12,05,8,0,0);
		LocalDateTime startT3 = LocalDateTime.of(2018,12,06,8,0,0);

		LocalDateTime createdT = LocalDateTime.of(2018,12,04,10,0,0);

		TimeNotAvailable tna1 = new TimeNotAvailable(uuid, null, startT1, created, null);
		TimeNotAvailable tna2 = new TimeNotAvailable(uuid, null, startT2, created, null);
		TimeNotAvailable tna3 = new TimeNotAvailable(uuid, null, startT3, created, null);

		String tna1UUID = tnaDAO.addTimeNotAvailable(tna1);
		String tna2UUID = tnaDAO.addTimeNotAvailable(tna2);
		String tna3UUID = tnaDAO.addTimeNotAvailable(tna3);


		GetScheduleRequest req = new GetScheduleRequest(uuid);
		TestAPIGatewayRequest request = new TestAPIGatewayRequest();

		request.addQueryParameter("uuid", uuid);
		request.setBody(LambdaHandler.gson.toJson(req));

		InputStream input = request.generateRequest(HTTPMethod.GET);
		OutputStream output = new ByteArrayOutputStream();
		Context context = request.generateContext("GetScheduleGET");

		GetScheduleHandler handler = new GetScheduleHandler();
		handler.handleRequest(input, output, context);

		LambdaResponse response = LambdaHandler.gson.fromJson(output.toString(), LambdaResponse.class);
		GetScheduleGETResponse res = LambdaHandler.gson.fromJson(response.body, GetScheduleGETResponse.class);

		Assert.assertEquals(3, res.meetings.size());
		Assert.assertEquals(3, res.timesNotAvailable.size());
	}
}
