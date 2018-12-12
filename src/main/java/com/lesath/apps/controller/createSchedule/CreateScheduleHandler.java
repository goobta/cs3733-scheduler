package com.lesath.apps.controller.createSchedule;

import com.lesath.apps.controller.APIGatewayRequest;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.model.ScheduleConfig;
import com.lesath.apps.db.ScheduleDAO;
import com.lesath.apps.util.HTTPMethod;

import java.util.ArrayList;

public class CreateScheduleHandler extends LambdaHandler {
    @Override
    protected boolean init() {
        this.controllerName = "CreateSchedule";

        this.handledMethods = new ArrayList<>();
        handledMethods.add(HTTPMethod.PUT);
        handledMethods.add(HTTPMethod.DELETE);

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
        
    	String scheduleId = request.queryStringParameters.get("scheduleId");
    	System.out.println("scheduleID");
    	System.out.println(scheduleId);
    	ScheduleDAO sDao = new ScheduleDAO();
    	try {
    			boolean boo = sDao.deleteSchedule(scheduleId);
    			if(!boo) {
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