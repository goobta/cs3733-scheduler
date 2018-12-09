package com.lesath.apps.controller.toggleTimeSlot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.LambdaResponse;
import com.lesath.apps.controller.TestAPIGatewayRequest;
import com.lesath.apps.controller.createMeeting.CreateMeetingHandler;
import com.lesath.apps.controller.createMeeting.CreateMeetingPUTResponse;
import com.lesath.apps.controller.model.ToggleSlotClass;
import com.lesath.apps.util.HTTPMethod;

public class TestToggleTimeSlot {
	
	LocalDateTime l = LocalDateTime.now();
	
	
	@Test
	public void addTimesNotAvailable() throws IOException, ParseException{
		
		ToggleSlotClass inp = new ToggleSlotClass(l, false);
		TestAPIGatewayRequest req = new TestAPIGatewayRequest();
		req.setBody(LambdaHandler.gson.toJson(inp));
		req.addQueryParameter("scheduleId", "82e712aa-d528-4f19-a44c-0835f54b91ee");
		
	
		
		InputStream input = req.generateRequest(HTTPMethod.POST);
        OutputStream output = new ByteArrayOutputStream();
        Context context = req.generateContext("ToggleTimeSlotPOST");
        
    
        ToggleTimeSlotHandler toggleTimeSlotHandler = new ToggleTimeSlotHandler();
        toggleTimeSlotHandler.handleRequest(input, output, context);
        
   
        
        LambdaResponse response = LambdaHandler.gson.fromJson(output.toString(), LambdaResponse.class);

        ToggleTimeSlotPOSTResponse resp = LambdaHandler.gson.fromJson(response.body, ToggleTimeSlotPOSTResponse.class);
        //Assert.assertNotNull(resp.meetingUuid);
      
        Assert.assertTrue(resp.status);
      
        
		
		
	}
	
	@Test
	public void removeTimesNotAvailable() throws IOException, ParseException{
		
		
		//Individual testing for local date time.
		LocalDateTime k = LocalDateTime.parse("2018-12-08 20:34:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		ToggleSlotClass inp = new ToggleSlotClass(k, true);
		TestAPIGatewayRequest req = new TestAPIGatewayRequest();
		req.setBody(LambdaHandler.gson.toJson(inp));
		req.addQueryParameter("scheduleId", "06807e2e-51dd-4b9c-bda2-107d411b0002");
		
		
		
		InputStream input = req.generateRequest(HTTPMethod.POST);
        OutputStream output = new ByteArrayOutputStream();
        Context context = req.generateContext("ToggleTimeSlotPOST");
        
       
        
        ToggleTimeSlotHandler toggleTimeSlotHandler = new ToggleTimeSlotHandler();
        toggleTimeSlotHandler.handleRequest(input, output, context);
        
        
        
        LambdaResponse response = LambdaHandler.gson.fromJson(output.toString(), LambdaResponse.class);
  
        ToggleTimeSlotPOSTResponse resp = LambdaHandler.gson.fromJson(response.body, ToggleTimeSlotPOSTResponse.class);
        //Assert.assertNotNull(resp.meetingUuid);
        
   
        Assert.assertFalse(resp.status);
       
		
	}
}
