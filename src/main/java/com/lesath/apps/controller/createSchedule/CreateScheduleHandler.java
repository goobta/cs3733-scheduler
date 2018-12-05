package com.lesath.apps.controller.createSchedule;

import com.lesath.apps.controller.APIGatewayRequest;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.model.ScheduleConfig;
import com.lesath.apps.util.HTTPMethod;

import java.util.ArrayList;

public class CreateScheduleHandler extends LambdaHandler {
    @Override
    protected boolean init() {
        this.controllerName = "CreateSchedule";

        this.handledMethods = new ArrayList<>();
        handledMethods.add(HTTPMethod.PUT);

        return true;
    }

    @Override
    protected boolean handlePUT(APIGatewayRequest request) {
        ScheduleConfig sc = gson.fromJson(request.body, ScheduleConfig.class);

        String uuid = new CreateScheduleRequest(sc).execute();

        CreateScheduleResponse res;
        if(!uuid.isEmpty()) {
            res = new CreateScheduleResponse(uuid);

            this.response.setStatusCode(200);
            this.response.setBody(gson.toJson(res));
            return true;
        } 
        else {
            this.response.setStatusCode(500);
            return false;
        }

        
    }

    @Override
    protected boolean handlePOST(APIGatewayRequest request) {
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