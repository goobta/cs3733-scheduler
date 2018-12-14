package extendSchedule;

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
import com.lesath.apps.controller.extendSchedule.ExtendScheduleHandler;
import com.lesath.apps.controller.model.ExtendSchedule;
import com.lesath.apps.controller.model.ScheduleConfig;
import com.lesath.apps.controller.toggleTimeSlot.ToggleTimeSlotHandler;
import com.lesath.apps.controller.toggleTimeSlot.ToggleTimeSlotPOSTResponse;
import com.lesath.apps.util.HTTPMethod;

public class TestExtendScheduleHandler {

	@Test
    public void AddScheduleSuccess() throws IOException, ParseException {
        	
        ExtendSchedule es = new ExtendSchedule(1);
        TestAPIGatewayRequest req = new TestAPIGatewayRequest();
        req.setBody(LambdaHandler.gson.toJson(es));
		req.addQueryParameter("scheduleId", "7p7945b2-886f-4bdf-a1f3-0d2ca75960bb");

		InputStream input = req.generateRequest(HTTPMethod.POST);
        OutputStream output = new ByteArrayOutputStream();
        Context context = req.generateContext("ExtendSchedulePOSTResponse");
        
        ExtendScheduleHandler extendScheduleHandler = new ExtendScheduleHandler();
        extendScheduleHandler.handleRequest(input, output, context);
        
        LambdaResponse response = LambdaHandler.gson.fromJson(output.toString(), LambdaResponse.class);

        ExtendSchedulePOSTResponse resp = LambdaHandler.gson.fromJson(response.body, ExtendSchedulePOSTResponse.class);
        
        Assert.assertFalse(resp.boo);
	}
        
}
