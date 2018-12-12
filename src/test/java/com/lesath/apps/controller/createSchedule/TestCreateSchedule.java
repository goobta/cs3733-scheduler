package com.lesath.apps.controller.createSchedule;

import com.amazonaws.services.lambda.runtime.Context;
import com.lesath.apps.controller.*;
import com.lesath.apps.controller.model.ScheduleConfig;
import com.lesath.apps.util.HTTPMethod;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

public class TestCreateSchedule {
   
	
	@Test
    public void AddScheduleSuccess() throws IOException, ParseException {
        ScheduleConfig sc = new ScheduleConfig(
                "CreateScheduleHandlerTest",
                60,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        TestAPIGatewayRequest req = new TestAPIGatewayRequest();
        req.setBody(LambdaHandler.gson.toJson(sc));

        InputStream input = req.generateRequest(HTTPMethod.PUT);
        OutputStream output = new ByteArrayOutputStream();
        Context context = req.generateContext("CreateSchedulePUT");

        CreateScheduleHandler createScheduleHandler = new CreateScheduleHandler();
        createScheduleHandler.handleRequest(input, output, context);

        LambdaResponse response = LambdaHandler.gson.fromJson(output.toString(), LambdaResponse.class);
        CreateSchedulePUTResponse resp = LambdaHandler.gson.fromJson(response.body, CreateSchedulePUTResponse.class);

        Assert.assertNotNull(resp.scheduleUuid);
    }
    
    
    @Test
    public void RemoveScheduleSuccess() throws IOException, ParseException {
        

        TestAPIGatewayRequest req = new TestAPIGatewayRequest();
        req.addQueryParameter("scheduleId", "ggfa4b58-b7d8-40ab-b192-c62492a8a565");

        InputStream input = req.generateRequest(HTTPMethod.DELETE);
        OutputStream output = new ByteArrayOutputStream();
        Context context = req.generateContext("CreateScheduleDELETE");

        CreateScheduleHandler createScheduleHandler = new CreateScheduleHandler();
        createScheduleHandler.handleRequest(input, output, context);

        LambdaResponse response = LambdaHandler.gson.fromJson(output.toString(), LambdaResponse.class);
        Assert.assertEquals(response.statusCode, 404);
    }
}
