package com.lesath.apps.controller.toggleTimeSlot;

import java.util.ArrayList;

import com.lesath.apps.controller.APIGatewayRequest;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.model.ToggleSlotClass;
import com.lesath.apps.util.HTTPMethod;

public class ToggleTimeSlotHandler extends LambdaHandler {

    @Override
    protected boolean init() {
        this.controllerName = "ToggleTimeSlot";

        this.handledMethods = new ArrayList<>();
        handledMethods.add(HTTPMethod.POST);

        return true;
    }
    
    @Override
    protected boolean handlePOST(APIGatewayRequest request) {
    	
    	logger.log("starating ToggleTimeSlotLambda");
    	
    	 ToggleSlotClass t = gson.fromJson(request.body, ToggleSlotClass.class);
    	logger.log("meeting input");
    	logger.log(request.body);
    	//String scheduleId = request.pathParameters.get("scheduleId");
    	String scheduleId = request.queryStringParameters.get("scheduleId");
    	logger.log("scheduleId");
    	logger.log(scheduleId);
    	boolean boo = new ToggleTimeSlotRequest(scheduleId, t).execute();
    	logger.log("bool");
    	if(boo) {
    		logger.log("True");
    	}
    	else {
    		logger.log("False");
    	}
    	
    	if(boo) {
    		this.response.setStatusCode(204);
    		return true;
    	}
    	else {
    		this.response.setStatusCode(400);
    	}
    	
    	
    	
    	
    	
        return true;
    }

    @Override
    protected boolean handlePUT(APIGatewayRequest request) {
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
