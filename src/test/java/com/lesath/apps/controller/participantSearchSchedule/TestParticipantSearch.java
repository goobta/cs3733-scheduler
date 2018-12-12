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
    public void testTimeSlotCreation() {
        ParticipantSearchScheduleRequest req = new ParticipantSearchScheduleRequest(null, scheduleId);

        Assert.assertEquals(5, req.generateAllAvailableTimeslots(schedule).size());
    }

    @Test
    public void testNoQuery() throws Exception {
        ScheduleQuery query = new ScheduleQuery();
        ParticipantSearchScheduleRequest request = new ParticipantSearchScheduleRequest(query, scheduleId);
        Assert.assertEquals(3, request.execute().size());
    }

    @Test
    public void testStartTimeLate() throws Exception {
        ScheduleQuery query = new ScheduleQuery();
        query.setStartTime(LocalDateTime.of(2018, 12, 04, 11, 0, 0));
        ParticipantSearchScheduleRequest request = new ParticipantSearchScheduleRequest(query, scheduleId);
        Assert.assertEquals(2, request.execute().size());
    }

    @Test
    public void testStartTimeEarlier() throws Exception {
        ScheduleQuery query = new ScheduleQuery();
        query.setStartTime(LocalDateTime.of(2018, 12, 04, 10, 0, 0));
        ParticipantSearchScheduleRequest request = new ParticipantSearchScheduleRequest(query, scheduleId);
        Assert.assertEquals(3, request.execute().size());
    }

    @Test
    public void testEndTime() throws Exception {
        ScheduleQuery query = new ScheduleQuery();
        query.setEndTime(LocalDateTime.of(2018, 12, 04, 12, 0, 0));
        ParticipantSearchScheduleRequest request = new ParticipantSearchScheduleRequest(query, scheduleId);
        Assert.assertEquals(2, request.execute().size());
    }

    @Test
    public void testSameDayOfWeek() throws Exception {
        ScheduleQuery query = new ScheduleQuery();
        query.setDayOfTheWeek(2);
        ParticipantSearchScheduleRequest request = new ParticipantSearchScheduleRequest(query, scheduleId);
        Assert.assertEquals(3, request.execute().size());
    }

    @Test
    public void testDifferentDayOfWeek() throws Exception {
        ScheduleQuery query = new ScheduleQuery();
        query.setDayOfTheWeek(3);
        ParticipantSearchScheduleRequest request = new ParticipantSearchScheduleRequest(query, scheduleId);
        Assert.assertEquals(0, request.execute().size());
    }

    @Test
    public void testSameMonth() throws Exception {
        ScheduleQuery query = new ScheduleQuery();
        query.setMonth(12);
        ParticipantSearchScheduleRequest request = new ParticipantSearchScheduleRequest(query, scheduleId);
        Assert.assertEquals(3, request.execute().size());
    }

    @Test
    public void testDifferentMonth() throws Exception {
        ScheduleQuery query = new ScheduleQuery();
        query.setMonth(11);
        ParticipantSearchScheduleRequest request = new ParticipantSearchScheduleRequest(query, scheduleId);
        Assert.assertEquals(0, request.execute().size());
    }

    @Test
    public void testSameYear() throws Exception {
        ScheduleQuery query = new ScheduleQuery();
        query.setYear(2018);
        ParticipantSearchScheduleRequest request = new ParticipantSearchScheduleRequest(query, scheduleId);
        Assert.assertEquals(3, request.execute().size());
    }

    @Test
    public void testDifferentYear() throws Exception {
        ScheduleQuery query = new ScheduleQuery();
        query.setYear(2017);
        ParticipantSearchScheduleRequest request = new ParticipantSearchScheduleRequest(query, scheduleId);
        Assert.assertEquals(0, request.execute().size());
    }

    @Test
    public void testSameDay() throws Exception {
        ScheduleQuery query = new ScheduleQuery();
        query.setDay(4);
        ParticipantSearchScheduleRequest request = new ParticipantSearchScheduleRequest(query, scheduleId);
        Assert.assertEquals(3, request.execute().size());
    }

    @Test
    public void testDifferentDay() throws Exception {
        ScheduleQuery query = new ScheduleQuery();
        query.setDay(5);
        ParticipantSearchScheduleRequest request = new ParticipantSearchScheduleRequest(query, scheduleId);
        Assert.assertEquals(0, request.execute().size());
    }

    @Test
    public void testSameCombined() throws Exception {
        ScheduleQuery query = new ScheduleQuery();
        query.setYear(2018)
                .setMonth(12)
                .setDay(4)
                .setDayOfTheWeek(2)
                .setStartTime(LocalDateTime.of(2018, 12, 04, 11, 0, 0))
                .setEndTime(LocalDateTime.of(2018, 12, 04, 12, 0, 0));


        ParticipantSearchScheduleRequest request = new ParticipantSearchScheduleRequest(query, scheduleId);
        Assert.assertEquals(1, request.execute().size());
    }

    @Test
    public void testCombinedOneFailure() throws Exception {
        ScheduleQuery query = new ScheduleQuery();
        query.setYear(2018)
                .setMonth(12)
                .setDay(4)
                .setDayOfTheWeek(3)
                .setStartTime(LocalDateTime.of(2018, 12, 04, 11, 0, 0))
                .setEndTime(LocalDateTime.of(2018, 12, 04, 12, 0, 0));


        ParticipantSearchScheduleRequest request = new ParticipantSearchScheduleRequest(query, scheduleId);
        Assert.assertEquals(0, request.execute().size());
    }
}
