package com.lesath.apps.controller.participantSearchSchedule;

import com.lesath.apps.controller.model.ScheduleQuery;
import com.lesath.apps.db.ScheduleDAO;
import com.lesath.apps.model.Schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
    }

    private ArrayList<LocalDateTime> generateAllAvailableTimeslots(Schedule schedule) {

    }
}
