package com.example.android.project.calendarofevents;

/**
 * Created by ramanan_ramesh on 01-Oct-17.
 */

public class CalendarOfEvents {
    String day;
    String event;
    int DOW;

    public CalendarOfEvents() {
    }

    public CalendarOfEvents(String day, String event) {
        this.day = day;
        this.event = event;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getDOW() {
        return DOW;
    }
}
