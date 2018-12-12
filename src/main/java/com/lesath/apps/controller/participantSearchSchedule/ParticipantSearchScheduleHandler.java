package com.lesath.apps.controller.participantSearchSchedule;

import com.lesath.apps.controller.APIGatewayRequest;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.util.HTTPMethod;

import java.util.ArrayList;

public class ParticipantSearchScheduleHandler extends LambdaHandler {
    @Override
    protected boolean init() {
        this.controllerName = "Participant Search Schedule";

        this.handledMethods = new ArrayList<>();
        this.handledMethods.add(HTTPMethod.POST);

        return true;
    }

    @Override
    protected boolean handlePUT(APIGatewayRequest request) {
        return false;
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
