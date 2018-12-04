package com.lesath.apps.controller.createSchedule;

import com.amazonaws.services.lambda.runtime.Context;
import com.lesath.apps.controller.CreateScheduleHandler;
import com.lesath.apps.controller.Handler;
import com.lesath.apps.controller.model.ScheduleConfig;
import com.lesath.apps.controller.util.APIGatewayRequest;
import com.lesath.apps.controller.util.LambdaResponse;
import com.lesath.apps.util.HTTPMethod;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

public class TestCreateSchedule {
    @Test
    public void AddScheduleSuccess() throws IOException {
        ScheduleConfig sc = new ScheduleConfig(
                "CreateScheduleHandlerTest",
                60,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        APIGatewayRequest req = new APIGatewayRequest();
        req.setBody(Handler.gson.toJson(sc));

        InputStream input = req.generateRequest(HTTPMethod.PUT);
        OutputStream output = new ByteArrayOutputStream();
        Context context = req.generateContext("CreateSchedulePUT") ;

        CreateScheduleHandler createScheduleHandler = new CreateScheduleHandler();
        createScheduleHandler.handleRequest(input, output, context);

        LambdaResponse response = Handler.gson.fromJson(output.toString(), LambdaResponse.class);
        CreateSchedulePUTResponse resp = Handler.gson.fromJson(response.body, CreateSchedulePUTResponse.class);

        Assert.assertNotNull(resp.scheduleUuid);
    }
}
