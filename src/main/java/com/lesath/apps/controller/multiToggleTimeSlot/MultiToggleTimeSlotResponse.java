package com.lesath.apps.controller.multiToggleTimeSlot;


import java.time.LocalDateTime;
import java.util.HashMap;

public class MultiToggleTimeSlotResponse {
    HashMap<LocalDateTime, Boolean> results;

    MultiToggleTimeSlotResponse(HashMap<LocalDateTime, Boolean> res) {
        this.results = res;
    }
}
