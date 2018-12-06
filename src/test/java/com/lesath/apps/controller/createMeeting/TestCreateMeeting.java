package com.lesath.apps.controller.createMeeting;

import com.amazonaws.services.lambda.runtime.Context;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.LambdaResponse;
import com.lesath.apps.controller.TestAPIGatewayRequest;
import com.lesath.apps.controller.model.MeetingInput;
import com.lesath.apps.db.MeetingDAO;
import com.lesath.apps.model.Meeting;
import com.lesath.apps.util.HTTPMethod;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;


public class TestCreateMeeting {

	@Test
	public void addMeeting() throws IOException, ParseException{
		
		MeetingInput inp = new MeetingInput(null, "Rt", LocalDateTime.now());
		TestAPIGatewayRequest req = new TestAPIGatewayRequest();
		req.setBody(LambdaHandler.gson.toJson(inp));
		req.addQueryParameter("scheduleId", "82e712aa-d528-4f19-a44c-0835f54b91ee");
        
		InputStream input = req.generateRequest(HTTPMethod.PUT);
        OutputStream output = new ByteArrayOutputStream();
        Context context = req.generateContext("CreateSchedulePUT");
        
        CreateMeetingHandler createMeetingHandler = new CreateMeetingHandler();
        createMeetingHandler.handleRequest(input, output, context);
        
        LambdaResponse response = LambdaHandler.gson.fromJson(output.toString(), LambdaResponse.class);
        CreateMeetingPUTResponse resp = LambdaHandler.gson.fromJson(response.body, CreateMeetingPUTResponse.class);
        //Assert.assertNotNull(resp.meetingUuid);
     
        Assert.assertNotEquals(resp.meetingUuid, "");
        
	}

	@Test
	public void deleteMeeting() throws Exception {
		TestAPIGatewayRequest request = new TestAPIGatewayRequest();
		Meeting meeting = new Meeting("82e712aa-d528-4f19-a44c-0835f54b91ee", null, LocalDateTime.now(),
				LocalDateTime.now(), null, "TestBoi");

		MeetingDAO dao = new MeetingDAO();
		String meetingId = dao.addMeeting(meeting);

		request.addQueryParameter("scheduleId", "82e712aa-d528-4f19-a44c-0835f54b91ee");
		request.addQueryParameter("meetingId", meetingId);

		InputStream input = request.generateRequest(HTTPMethod.DELETE);
		OutputStream output = new ByteArrayOutputStream();
		Context context = request.generateContext("CreateScheduleDELETE");

		CreateMeetingHandler handler = new CreateMeetingHandler();
		handler.handleRequest(input, output, context);

		LambdaResponse respose = LambdaHandler.gson.fromJson(output.toString(), LambdaResponse.class);

		Assert.assertEquals(respose.statusCode, 204);
	}
}
