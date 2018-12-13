package com.lesath.apps.controller.sysAdminDaysSearch;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.reflect.TypeToken;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.LambdaResponse;
import com.lesath.apps.controller.TestAPIGatewayRequest;
import com.lesath.apps.controller.sysAdminDaySearch.SysAdminDaySearchHandler;
import com.lesath.apps.db.ScheduleDAO;
import com.lesath.apps.model.Schedule;
import com.lesath.apps.util.HTTPMethod;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;

public class TestSysAdminDaysSearch {
    ScheduleDAO sdao;

    Schedule sched1;
    Schedule sched2;
    Schedule sched3;

    String uuid1;
    String uuid2;
    String uuid3;

    @Before
    public void addSchedules() throws Exception {
        sdao = new ScheduleDAO();

        LocalDate start_date = LocalDate.of(2018,12,04);
        LocalDate end_date = LocalDate.of(2018,12,05);
        LocalTime daily_start_time = LocalTime.of(8, 0);
        LocalTime daily_end_time = LocalTime.of(11, 0);

        sched1 = new Schedule(null, "TestSchedule", 30, start_date, end_date, daily_start_time, daily_end_time, null, null);
        sched2 = new Schedule(null, "TestSchedule2", 30, start_date, end_date, daily_start_time, daily_end_time, null, null);
        sched3 = new Schedule(null, "TestSchedule3", 30, start_date, end_date, daily_start_time, daily_end_time, null, null);

        uuid1 = sdao.addSchedule(sched1);
        uuid2 = sdao.addSchedule(sched2);
        uuid3 = sdao.addSchedule(sched3);

        sched1.setUuid(uuid1);
        sched2.setUuid(uuid2);
        sched3.setUuid(uuid3);

        ArrayList<Schedule> scheds = sdao.getSchedulesDaysOld(0);
        assertFalse(scheds.isEmpty());

        int got = 0;
        for(Schedule s: scheds) {
            if(s.equals(sched1) || s.equals(sched2) || s.equals(sched3)) {
                got++;
            }
        }
        TimeUnit.SECONDS.sleep(1);
        Assert.assertFalse(got == 0);
    }

    @Test
    public void testNDaysOldSchedulesGET() throws IOException {
        TestAPIGatewayRequest req = new TestAPIGatewayRequest();
        req.addQueryParameter("days", "0");

        InputStream input = req.generateRequest(HTTPMethod.GET);
        OutputStream output = new ByteArrayOutputStream();
        Context context = req.generateContext("Query N Days Old");

        SysAdminDaySearchHandler handler = new SysAdminDaySearchHandler();
        handler.handleRequest(input, output, context);;

        LambdaResponse response = LambdaHandler.gson.fromJson(output.toString(), LambdaResponse.class);
        ArrayList<Schedule> schedules = LambdaHandler.gson.fromJson(response.body, new TypeToken<ArrayList<Schedule>>(){}.getType());

        int got = 0;
        for(Schedule s: schedules) {
            if(s.equals(sched1) || s.equals(sched2) || s.equals(sched3)) {
                got++;
            }
        }

        Assert.assertEquals(3, got);
    }

    @Test
    public void testNDaysOldSchedulesDELETE() throws Exception {
        TestAPIGatewayRequest req = new TestAPIGatewayRequest();
        req.addQueryParameter("days", "0");

        InputStream input = req.generateRequest(HTTPMethod.DELETE);
        OutputStream output = new ByteArrayOutputStream();
        Context context = req.generateContext("DELETE N Days Old");

        SysAdminDaySearchHandler handler = new SysAdminDaySearchHandler();
        handler.handleRequest(input, output, context);

        Assert.assertTrue(sdao.getSchedulesDaysOld(1000).isEmpty());
    }
}
