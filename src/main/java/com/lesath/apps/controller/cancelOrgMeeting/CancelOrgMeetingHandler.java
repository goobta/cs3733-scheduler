package com.lesath.apps.controller.cancelOrgMeeting;

import java.util.ArrayList;

import com.lesath.apps.controller.APIGatewayRequest;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.createMeeting.CreateMeetingRequest;
import com.lesath.apps.controller.createMeeting.CreateMeetingResponse;
import com.lesath.apps.controller.model.MeetingInput;
import com.lesath.apps.db.MeetingDAO;
import com.lesath.apps.util.HTTPMethod;

public class CancelOrgMeetingHandler extends LambdaHandler {
	@Override
    protected boolean init() {
        this.controllerName = "CancelOrgMeeting";

        this.handledMethods = new ArrayList<>();
        handledMethods.add(HTTPMethod.DELETE);

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
        String scheduleId = request.queryStringParameters.get("scheduleId");
        String meetingId = request.queryStringParameters.get("meetingId");

        MeetingDAO dao = new MeetingDAO();
        try {
        	dao.deleteMeeting(meetingId);
            this.response.setStatusCode(204);
            return true;
        } catch (Exception e) {
            this.response.setStatusCode(404);
            return false;
        }
    }
}
