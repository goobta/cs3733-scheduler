package com.lesath.apps.controller.createMeeting;

import java.util.ArrayList;

import com.lesath.apps.controller.APIGatewayRequest;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.model.MeetingInput;
import com.lesath.apps.util.HTTPMethod;

public class CreateMeetingHandler extends LambdaHandler {

    @Override
    protected boolean init() {
        this.controllerName = "CreateMeeting";

        this.handledMethods = new ArrayList<>();
        handledMethods.add(HTTPMethod.PUT);

        return true;
    }
    
    @Override
    protected boolean handlePUT(APIGatewayRequest request) {
    	
    	MeetingInput m = gson.fromJson(request.body, MeetingInput.class);
    	String scheduleId = request.pathParameters.get("scheduleId");
    	
    	String uuid = new CreateMeetingRequest(m, scheduleId).execute();
    	
    	CreateMeetingResponse res;
    	if(uuid != null) {
    		res = new CreateMeetingResponse(uuid);
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