package com.lesath.apps.controller.participantSearchSchedule;

import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.Month;

public class TestAssumptions {
    @Test
    public void testAssumptions() {
        Assert.assertEquals(1, DayOfWeek.MONDAY.getValue());
        Assert.assertEquals(5, DayOfWeek.FRIDAY.getValue());
        Assert.assertEquals(1, Month.JANUARY.getValue());
    }
}
