package com.example.android.project;

/**
 * Created by ramanan_ramesh on 24-Sep-17.
 */

public class Timetable {

    String timings;
    String subCode;

    public Timetable(String timings, String subCode) {
        this.timings = timings;
        this.subCode = subCode;
    }

    public Timetable() {
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    /*public static Comparator<Timetable> timetableComparator= new Comparator<Timetable>() {
        @Override
        public int compare(Timetable timetable, Timetable t1) {
            String time1=timetable.getTimings();
            String time2=t1.getTimings();
            return time1.compareTo(time2);
        }
    };*/

}
