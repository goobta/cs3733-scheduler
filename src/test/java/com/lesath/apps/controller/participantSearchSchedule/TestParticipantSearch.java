package com.lesath.apps.controller.participantSearchSchedule;

import com.lesath.apps.db.ScheduleDAO;
import com.lesath.apps.model.Schedule;
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

        LocalDate start_date = LocalDate.of(2018,12,04);
        LocalDate end_date = LocalDate.of(2018,12,04);
        LocalTime daily_start_time = LocalTime.of(8, 0);
        LocalTime daily_end_time = LocalTime.of(11, 0);
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

        Assert.assertEquals(3, req.generateAllAvailableTimeslots(schedule).size());
    }
}
