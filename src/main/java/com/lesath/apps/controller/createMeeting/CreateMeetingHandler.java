package com.lesath.apps.controller.createMeeting;

import java.util.ArrayList;

import com.lesath.apps.controller.APIGatewayRequest;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.util.HTTPMethod;

public class CreateMeetingHandler extends LambdaHandler {

    @Override
    protected boolean init() {
        this.controllerName = "CreateSchedule";

        this.handledMethods = new ArrayList<>();
        handledMethods.add(HTTPMethod.PUT);

        return true;
    }
    
    @Override
    protected boolean handlePUT(APIGatewayRequest request) {

        return true;
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
