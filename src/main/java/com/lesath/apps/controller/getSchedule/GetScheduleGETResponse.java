package com.lesath.apps.controller.getSchedule;

import com.google.gson.annotations.SerializedName;
import com.lesath.apps.db.MeetingDAO;
import com.lesath.apps.db.TimesNotAvailableDAO;
import com.lesath.apps.model.Meeting;
import com.lesath.apps.model.Schedule;
import com.lesath.apps.model.TimeNotAvailable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GetScheduleGETResponse {
    @SerializedName("id")
    String uuid;

    String name;
    String organizerId = "01e113d8-3cc3-4fc1-90a2-81f65cfcda16";
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;

    @SerializedName("meetingDuration")
    int duration;

    LocalDateTime created_at;
    LocalDateTime deleted_at;

    List<Meeting> meetings;
    List<TimeNotAvailable> timesNotAvailableObj;

    List<LocalDateTime> timesNotAvailable;

    public GetScheduleGETResponse(Schedule s) {
        this.uuid = s.getUuid();
        this.name = s.getName();
        this.duration = s.getDuration();
        this.created_at = s.getCreated_at();
        this.deleted_at = s.getDeleted_at();

        this.startDateTime = s.getStartDateTime();
        this.endDateTime = s.getEndDateTime();
    }

    boolean loadMeetingsAndTimesNotAvailable () {
        return this.getMeetings() && this.getTimesNotAvailable();
    }

    private boolean getMeetings() {
        MeetingDAO mDAO = new MeetingDAO();

        try {
            this.meetings = mDAO.getAllMeetingsForSchedule(this.uuid);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean getTimesNotAvailable() {
        TimesNotAvailableDAO tnaDAO = new TimesNotAvailableDAO();

        try {
            this.timesNotAvailableObj = tnaDAO.getAllTimesNotAvailableForScheduleId(this.uuid);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void generateRequest() {
        this.timesNotAvailable = new ArrayList<>();

        System.out.println("TIMES NOT AVAILABLE: " + this.timesNotAvailableObj);

        if(this.timesNotAvailableObj == null) return;

        for (TimeNotAvailable timeNotAvailable : this.timesNotAvailableObj) {
            this.timesNotAvailable.add(timeNotAvailable.getStart_time());
        }
    }
}
