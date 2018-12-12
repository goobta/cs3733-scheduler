package com.lesath.apps.controller.participantSearchSchedule;

import com.google.gson.JsonParseException;
import com.lesath.apps.controller.APIGatewayRequest;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.model.ScheduleQuery;
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
        logger.log("Starting POST method");

        String scheduleId;
        ScheduleQuery query;

        try {
            scheduleId = request.queryStringParameters.get("scheduleId");
            query = gson.fromJson(request.body, ScheduleQuery.class);
        } catch (JsonParseException e) {
            e.printStackTrace();

            this.response.setStatusCode(400);
            return false;
        }

        ParticipantSearchScheduleRequest req = new ParticipantSearchScheduleRequest(query, scheduleId);



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
