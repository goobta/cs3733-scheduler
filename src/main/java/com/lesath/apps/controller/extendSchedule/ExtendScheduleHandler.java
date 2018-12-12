package com.lesath.apps.controller.extendSchedule;

import java.util.ArrayList;

import com.lesath.apps.controller.APIGatewayRequest;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.createSchedule.CreateScheduleRequest;
import com.lesath.apps.controller.createSchedule.CreateScheduleResponse;
import com.lesath.apps.controller.model.ExtendSchedule;
import com.lesath.apps.controller.model.ScheduleConfig;
import com.lesath.apps.util.HTTPMethod;

public class ExtendScheduleHandler extends LambdaHandler {
	
	@Override
    protected boolean init() {
        this.controllerName = "ExtendSchedule";

        this.handledMethods = new ArrayList<>();
        handledMethods.add(HTTPMethod.POST);

        return true;
    }

    @Override
    protected boolean handlePUT(APIGatewayRequest request) {
        return false;        
    }

    @Override
    protected boolean handlePOST(APIGatewayRequest request) {
    	logger.log("Entered Put");
    	ExtendSchedule ex = gson.fromJson(request.body, ExtendSchedule.class);
    	String scheduleId = request.queryStringParameters.get("scheduleId");
    	
        return false;
    }

    @Override
    protected boolean handleGET(APIGatewayRequest request) {
        return false;
    }

    @Override
    protected boolean handleDELETE(APIGatewayRequest request) {
        return false;
    }

}
