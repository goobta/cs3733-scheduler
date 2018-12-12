package com.lesath.apps.controller.multiToggleTimeSlot;

import com.amazonaws.services.lambda.runtime.Context;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.LambdaResponse;
import com.lesath.apps.controller.TestAPIGatewayRequest;
import com.lesath.apps.db.TimesNotAvailableDAO;
import com.lesath.apps.model.TimeNotAvailable;
import com.lesath.apps.util.HTTPMethod;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class testToggleTimeSlot {

    String scheduleId = "69696969-6969-6969-6969-420696969696";

    @Test
    public void addAndDeleteMultipleTimesNotAvailable() throws Exception {
        LocalDateTime time1 = LocalDateTime.parse("2018-12-08 20:34:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime time2 = LocalDateTime.parse("2018-12-08 21:34:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime time3 = LocalDateTime.parse("2018-12-08 22:34:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        MultiToggleTimeSlotRequest in = new MultiToggleTimeSlotRequest();
        in.scheduleId = scheduleId;

        in.times = new ArrayList<>();
        in.times.add(time1);
        in.times.add(time2);
        in.times.add(time3);

        TestAPIGatewayRequest request = new TestAPIGatewayRequest();
        request.setBody(LambdaHandler.gson.toJson(in));

        InputStream input = request.generateRequest(HTTPMethod.PUT);
        OutputStream output = new ByteArrayOutputStream();
        Context context = request.generateContext("MultiToggleTimeSlot PUT");

        MultiToggleTimeSlotHandler handler = new MultiToggleTimeSlotHandler();
        handler.handleRequest(input, output, context);

        TimesNotAvailableDAO tDao = new TimesNotAvailableDAO();

        ArrayList<TimeNotAvailable> results = tDao.getAllTimesNotAvailableForScheduleId(scheduleId);
        Assert.assertNotNull(results);
        Assert.assertEquals(3, results.size());

        input = request.generateRequest(HTTPMethod.DELETE);
        context = request.generateContext("MultiTogggleTimeSlot DELETE");

        handler.handleRequest(input, output, context);

        Assert.assertNull(tDao.getAllTimesNotAvailableForScheduleId(scheduleId));
    }
}
