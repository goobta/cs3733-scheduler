package com.lesath.apps.controller.participantSearchSchedule;

import com.amazonaws.services.lambda.runtime.Context;
import com.lesath.apps.controller.LambdaHandler;
import com.lesath.apps.controller.LambdaResponse;
import com.lesath.apps.controller.TestAPIGatewayRequest;
import com.lesath.apps.controller.model.ScheduleQuery;
import com.lesath.apps.db.MeetingDAO;
import com.lesath.apps.db.ScheduleDAO;
import com.lesath.apps.db.TimesNotAvailableDAO;
import com.lesath.apps.model.Meeting;
import com.lesath.apps.model.Schedule;
import com.lesath.apps.model.TimeNotAvailable;
import com.lesath.apps.util.HTTPMethod;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class TestParticipantSearchHandler {
    static String scheduleId;
    static Schedule schedule;

    @BeforeClass
    public static void addSchedule() throws Exception {
        ScheduleDAO sDAO = new ScheduleDAO();

        LocalDate start_date = LocalDate.of(2018, 12, 04);
        LocalDate end_date = LocalDate.of(2018, 12, 04);
        LocalTime daily_start_time = LocalTime.of(8, 0);
        LocalTime daily_end_time = LocalTime.of(13, 0);
        LocalDateTime created_at = LocalDateTime.of(2018, 6, 7, 7, 0);

        schedule = new Schedule(
                null,
                "TestSchedule",
                60,
                start_date,
                end_date,
                daily_start_time,
                daily_end_time,
                created_at,
                null);

        scheduleId = sDAO.addSchedule(schedule);

        MeetingDAO mdao = new MeetingDAO();

        LocalDateTime start = LocalDateTime.of(2018,12,04,8,0,0);
        LocalDateTime created = LocalDateTime.of(2018,12,04,10,0,0);
        Meeting meeting = new Meeting(
                scheduleId,
                null,
                start,
                created,
                null,
                "TestName");

        String meetingUUID = mdao.addMeeting(meeting);
        Assert.assertEquals(1, mdao.getAllMeetingsForSchedule(scheduleId).size());


        TimesNotAvailableDAO tnadao = new TimesNotAvailableDAO();
        LocalDateTime startT = LocalDateTime.of(2018,12,04,9,0,0);
        LocalDateTime createdT = LocalDateTime.of(2018,12,04,10,0,0);
        TimeNotAvailable tna = new TimeNotAvailable(
                scheduleId,
                null,
                startT,
                createdT,
                null);

        tnadao.addTimeNotAvailable(tna);
        Assert.assertEquals(1, tnadao.getAllTimesNotAvailableForScheduleId(scheduleId).size());
    }

    @Test
    public void testNoBlocks() throws IOException {
        ScheduleQuery query = new ScheduleQuery();

        TestAPIGatewayRequest req = new TestAPIGatewayRequest();
        req.addQueryParameter("scheduleId", scheduleId);
        req.setBody(LambdaHandler.gson.toJson(query));

        InputStream input = req.generateRequest(HTTPMethod.POST);
        OutputStream output = new ByteArrayOutputStream();
        Context context = req.generateContext("ParticipantSearchPOST");

        ParticipantSearchScheduleHandler handler = new ParticipantSearchScheduleHandler();
        handler.handleRequest(input, output, context);

        LambdaResponse response = LambdaHandler.gson.fromJson(output.toString(), LambdaResponse.class);
        ArrayList<LocalDateTime> results = LambdaHandler.gson.fromJson(response.body, ArrayList.class);

        Assert.assertEquals(3, results.size());
    }
}
