package com.lesath.apps.controller.multiToggleTimeSlot;

import com.google.gson.JsonParseException;
import com.lesath.apps.controller.APIGatewayRequest;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.util.HTTPMethod;

import java.util.ArrayList;

public class MultiToggleTimeSlotHandler extends LambdaHandler {
    @Override
    protected boolean init() {
        this.controllerName = "MultiToggleTimeSlot";

        this.handledMethods = new ArrayList<>();
        handledMethods.add(HTTPMethod.DELETE);
        handledMethods.add(HTTPMethod.PUT);

        return true;
    }

    @Override
    protected boolean handlePUT(APIGatewayRequest request) {
        logger.log("Starting PUT method");

        try {
            MultiToggleTimeSlotRequest req = gson.fromJson(request.body, MultiToggleTimeSlotRequest.class);
            req.availability = false;

            MultiToggleTimeSlotResponse response = new MultiToggleTimeSlotResponse(req.execute());

            this.response.setStatusCode(200);
            this.response.setBody(gson.toJson(response));
        } catch (JsonParseException e) {
            e.printStackTrace();

            this.response.setStatusCode(400);
        }

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
        logger.log("Starting delete method");

        try {
            MultiToggleTimeSlotRequest req = gson.fromJson(request.body, MultiToggleTimeSlotRequest.class);
            req.availability = true;

            MultiToggleTimeSlotResponse response = new MultiToggleTimeSlotResponse(req.execute());

            this.response.setStatusCode(200);
            this.response.setBody(gson.toJson(response));
        } catch (JsonParseException e) {
            e.printStackTrace();

            this.response.setStatusCode(400);
        }

        return true;
    }
}
