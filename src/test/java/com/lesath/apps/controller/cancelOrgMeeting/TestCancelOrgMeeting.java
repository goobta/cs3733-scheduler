package com.lesath.apps.controller.cancelOrgMeeting;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.LambdaResponse;
import com.lesath.apps.controller.TestAPIGatewayRequest;
import com.lesath.apps.controller.createMeeting.CreateMeetingHandler;
import com.lesath.apps.db.MeetingDAO;
import com.lesath.apps.model.Meeting;
import com.lesath.apps.util.HTTPMethod;

public class TestCancelOrgMeeting {

	@Test
	public void deleteMeeting() throws Exception {
		TestAPIGatewayRequest request = new TestAPIGatewayRequest();
		Meeting meeting = new Meeting("6969696969", null, LocalDateTime.now(),
				LocalDateTime.now(), null, "TestBoi");

		MeetingDAO dao = new MeetingDAO();
		String meetingId = dao.addMeeting(meeting);

		request.addQueryParameter("scheduleId", "82e712aa-d528-4f19-a44c-0835f54b91ee");
		request.addQueryParameter("meetingId", meetingId);

		InputStream input = request.generateRequest(HTTPMethod.DELETE);
		OutputStream output = new ByteArrayOutputStream();
		Context context = request.generateContext("CreateScheduleDELETE");

		CancelOrgMeetingHandler handler = new CancelOrgMeetingHandler();
		handler.handleRequest(input, output, context);

		LambdaResponse respose = LambdaHandler.gson.fromJson(output.toString(), LambdaResponse.class);

		Assert.assertEquals(respose.statusCode, 204);
	}
}
