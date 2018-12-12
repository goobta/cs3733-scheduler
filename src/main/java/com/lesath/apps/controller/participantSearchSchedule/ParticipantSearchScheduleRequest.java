package com.lesath.apps.controller.participantSearchSchedule;

import com.lesath.apps.controller.model.ScheduleQuery;
import com.lesath.apps.db.ScheduleDAO;
import com.lesath.apps.model.Schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class ParticipantSearchScheduleRequest {
    ScheduleQuery config;
    String scheduleId;

    ParticipantSearchScheduleRequest(ScheduleQuery config, String scheduleId) {
        this.config = config;
        this.scheduleId = scheduleId;
    }

    public ArrayList<LocalDateTime> execute() throws Exception {
        ScheduleDAO sDAO = new ScheduleDAO();
        Schedule s = sDAO.getSchedule(this.scheduleId);

        return null;
    }

    ArrayList<LocalDateTime> generateAllAvailableTimeslots(Schedule schedule) {
        ArrayList<LocalDateTime> spots = new ArrayList<>();

        LocalDateTime instant = schedule.getStartDateTime();

        for(int i = 0; i <= DAYS.between(schedule.getStart_date(), schedule.getEnd_date()); i++) {
            LocalDateTime endDateTime = LocalDateTime.of(schedule.getStart_date().plusDays(i), schedule.getDaily_end_time());

            while(instant.isBefore(endDateTime)) {
                spots.add(instant);

                instant = instant.plusMinutes(schedule.getDuration());
            }

            instant = instant.plusDays(1)
                    .withHour(schedule.getDaily_start_time().getHour())
                    .withMinute(schedule.getDaily_start_time().getMinute());
        }

        return spots;
    }
}
