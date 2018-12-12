/**
 * 
 */
package com.lesath.apps.controller.sysAdminHourSearch;

import java.util.ArrayList;

import com.lesath.apps.controller.APIGatewayRequest;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.db.ScheduleDAO;
import com.lesath.apps.model.Schedule;
import com.lesath.apps.util.HTTPMethod;

/**
 * @author Andy
 *
 */
public class SysAdminHourSearchHandler extends LambdaHandler {

	/* (non-Javadoc)
	 * @see com.lesath.apps.controller.LambdaHandler#init()
	 */
	@Override
	protected boolean init() {
		this.controllerName = "SysAdminDaySearch";

        this.handledMethods = new ArrayList<>();
        this.handledMethods.add(HTTPMethod.GET);

        return true;
	}

	/* (non-Javadoc)
	 * @see com.lesath.apps.controller.LambdaHandler#handlePUT(com.lesath.apps.controller.APIGatewayRequest)
	 */
	@Override
	protected boolean handlePUT(APIGatewayRequest request) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lesath.apps.controller.LambdaHandler#handlePOST(com.lesath.apps.controller.APIGatewayRequest)
	 */
	@Override
	protected boolean handlePOST(APIGatewayRequest request) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.lesath.apps.controller.LambdaHandler#handleGET(com.lesath.apps.controller.APIGatewayRequest)
	 */
	@Override
	protected boolean handleGET(APIGatewayRequest request) {
		try {
            int hours = Integer.parseInt(request.queryStringParameters.get("hours"));

            ScheduleDAO sDAO = new ScheduleDAO();
            ArrayList<Schedule> schedules = sDAO.getSchedulesHoursOld(hours);

            response.setStatusCode(200);
            response.setBody(gson.toJson(schedules));

        } catch (NullPointerException e) {
            e.printStackTrace();

            this.response.setStatusCode(400);
            return false;
        } catch (Exception e) {
            e.printStackTrace();

            this.response.setStatusCode(500);
            return false;
        }

        return true;
	}

	/* (non-Javadoc)
	 * @see com.lesath.apps.controller.LambdaHandler#handleDELETE(com.lesath.apps.controller.APIGatewayRequest)
	 */
	@Override
	protected boolean handleDELETE(APIGatewayRequest request) {
		return false;
	}

}
