package com.lesath.apps.controller.toggleTimeSlot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.LambdaResponse;
import com.lesath.apps.controller.TestAPIGatewayRequest;
import com.lesath.apps.controller.createMeeting.CreateMeetingPUTResponse;
import com.lesath.apps.controller.model.ToggleSlotClass;
import com.lesath.apps.util.HTTPMethod;

public class TestToggleTimeSlot {

	@Test
	public void addTimesNotAvailable() throws IOException, ParseException{
		ToggleSlotClass inp = new ToggleSlotClass(LocalDateTime.now(), true);
		TestAPIGatewayRequest req = new TestAPIGatewayRequest();
		req.setBody(LambdaHandler.gson.toJson(inp));
		req.addQueryParameter("scheduleId", "82e712aa-d528-4f19-a44c-0835f54b91ee");
		
		InputStream input = req.generateRequest(HTTPMethod.PUT);
        OutputStream output = new ByteArrayOutputStream();
        Context context = req.generateContext("ToggleTimeSlotPOST");
        
        LambdaResponse response = LambdaHandler.gson.fromJson(output.toString(), LambdaResponse.class);
        ToggleTimeSlotPOSTResponse resp = LambdaHandler.gson.fromJson(response.body, ToggleTimeSlotPOSTResponse.class);
        //Assert.assertNotNull(resp.meetingUuid);
     
        Assert.assertTrue(resp.boo);
        
		
		
	}
}
