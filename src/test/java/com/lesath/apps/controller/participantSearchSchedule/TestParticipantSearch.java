package com.lesath.apps.controller.participantSearchSchedule;

import com.lesath.apps.controller.model.ScheduleQuery;
import com.lesath.apps.db.MeetingDAO;
import com.lesath.apps.db.ScheduleDAO;
import com.lesath.apps.db.TimesNotAvailableDAO;
import com.lesath.apps.model.Meeting;
import com.lesath.apps.model.Schedule;
import com.lesath.apps.model.TimeNotAvailable;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TestParticipantSearch {
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
    }

    @Test
    public void testTimeSlotCreation() {
        ParticipantSearchScheduleRequest req = new ParticipantSearchScheduleRequest(null, scheduleId);

        Assert.assertEquals(5, req.generateAllAvailableTimeslots(schedule).size());
    }

    @Test
    public void testWithBlocks() throws Exception {
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


        ScheduleQuery query0 = new ScheduleQuery();
        ParticipantSearchScheduleRequest request0 = new ParticipantSearchScheduleRequest(query0, scheduleId);
        Assert.assertEquals(3, request0.execute().size());

        ScheduleQuery query1 = new ScheduleQuery();
        query1.setStartTime(LocalDateTime.of(2018,12,04,11,0,0));
        ParticipantSearchScheduleRequest request1 = new ParticipantSearchScheduleRequest(query1, scheduleId);
        Assert.assertEquals(2, request1.execute().size());

        ScheduleQuery query2 = new ScheduleQuery();
        query2.setStartTime(LocalDateTime.of(2018,12,05,10,0,0));
        ParticipantSearchScheduleRequest request2 = new ParticipantSearchScheduleRequest(query2, scheduleId);
        Assert.assertEquals(3, request2.execute().size());

        ScheduleQuery query3 = new ScheduleQuery();
        query2.setEndTime(LocalDateTime.of(2018,12,05,12,0,0));
        ParticipantSearchScheduleRequest request3 = new ParticipantSearchScheduleRequest(query3, scheduleId);
        Assert.assertEquals(2, request2.execute().size());
    }
}
