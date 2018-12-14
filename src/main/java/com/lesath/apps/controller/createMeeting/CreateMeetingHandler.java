package com.lesath.apps.controller.createMeeting;

import java.util.ArrayList;

import com.lesath.apps.controller.APIGatewayRequest;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.model.MeetingInput;
import com.lesath.apps.db.MeetingDAO;
import com.lesath.apps.util.HTTPMethod;

public class CreateMeetingHandler extends LambdaHandler {

    @Override
    protected boolean init() {
        this.controllerName = "CreateMeeting";

        this.handledMethods = new ArrayList<>();
        handledMethods.add(HTTPMethod.PUT);
        handledMethods.add(HTTPMethod.DELETE);

        return true;
    }
    
    @Override
    protected boolean handlePUT(APIGatewayRequest request) {
    	try {
	    	logger.log("starating createMeetingLambda");
	    	
	    	MeetingInput m = gson.fromJson(request.body, MeetingInput.class);
	    	logger.log("meeting input");
	    	logger.log(request.body);
	    	//String scheduleId = request.pathParameters.get("scheduleId");
	    	String scheduleId = request.queryStringParameters.get("scheduleId");
	    	logger.log("scheduleId");
	    	logger.log(scheduleId);
	    	String uuid = new CreateMeetingRequest(m, scheduleId).execute();
	    	logger.log("uuid");
	    	logger.log(uuid);
	    	CreateMeetingResponse res;
	    	if(uuid != null) {
	    		res = new CreateMeetingResponse(uuid);
	    		this.response.setStatusCode(200);
	            this.response.setBody(gson.toJson(res));
	            return true;
	    	}
	    	else {
	            this.response.setStatusCode(409);
	            return false;
	        }
    	} catch(Exception e) {
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
        String scheduleId = request.queryStringParameters.get("scheduleId");
        String meetingId = request.queryStringParameters.get("meetingId");

        MeetingDAO dao = new MeetingDAO();
        try {
        	boolean bool = dao.deleteMeeting(meetingId);
        	if(!bool) {
        		this.response.setStatusCode(404);
                return false;
        	}
            this.response.setStatusCode(204);
            return true;
        } catch(Exception e) {
            this.response.setStatusCode(404);
            return false;
        }
    }
}
