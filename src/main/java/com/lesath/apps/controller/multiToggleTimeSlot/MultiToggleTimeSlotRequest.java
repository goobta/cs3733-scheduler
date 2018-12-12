package com.lesath.apps.controller.multiToggleTimeSlot;

import com.lesath.apps.db.TimesNotAvailableDAO;
import com.lesath.apps.model.TimeNotAvailable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class MultiToggleTimeSlotRequest {
    ArrayList<LocalDateTime> times;
    String scheduleId;
    boolean availability;

    HashMap<LocalDateTime, Boolean> execute() {
        HashMap<LocalDateTime, Boolean> response = new HashMap<>();
        TimesNotAvailableDAO tDao = new TimesNotAvailableDAO();

        for(LocalDateTime dateTime : times) {
            try {
                if(!availability) {
                    TimeNotAvailable t = new TimeNotAvailable(scheduleId, null, dateTime, null, null);

                    tDao.addTimeNotAvailable(t);

                    response.put(dateTime, true);
                } else {
                    boolean success = tDao.deleteTimeNotAvailable(scheduleId, dateTime);

                    response.put(dateTime, success);
                }
            } catch (Exception e) {
                e.printStackTrace();

                response.put(dateTime, false);
            }
        }

        return response;
    }
}
