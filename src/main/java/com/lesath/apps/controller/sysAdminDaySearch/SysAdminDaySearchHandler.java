package com.lesath.apps.controller.sysAdminDaySearch;

import com.lesath.apps.controller.APIGatewayRequest;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.db.ScheduleDAO;
import com.lesath.apps.model.Schedule;
import com.lesath.apps.util.HTTPMethod;

import java.util.ArrayList;

public class SysAdminDaySearchHandler extends LambdaHandler {
    @Override
    protected boolean init() {
        this.controllerName = "SysAdminDaySearch";

        this.handledMethods = new ArrayList<>();
        this.handledMethods.add(HTTPMethod.GET);
        this.handledMethods.add(HTTPMethod.DELETE);

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
        try {
            int days = Integer.parseInt(request.queryStringParameters.get("days"));

            ScheduleDAO sDAO = new ScheduleDAO();
            ArrayList<Schedule> schedules = sDAO.getSchedulesDaysOld(days);

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

    @Override
    protected boolean handleDELETE(APIGatewayRequest request) {
        try {
            int days = Integer.parseInt(request.queryStringParameters.get("days"));

            ScheduleDAO sDAO = new ScheduleDAO();
            ArrayList<Schedule> schedules = sDAO.getSchedulesDaysOld(days);

            if (schedules == null || schedules.size() == 0) {
                this.response.setStatusCode(204);
                return true;
            }

            ArrayList<String> uuids = new ArrayList<>();

            for(Schedule s : schedules) {
                uuids.add(s.getUuid());
            }

            sDAO.deleteSchedules(uuids);

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
}
