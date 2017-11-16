package com.example.android.project;

/**
 * Created by ramanan_ramesh on 08-Nov-17.
 */

public class Student {
    String USN, name, sec;
    int depID, sem;

    public Student(String USN, String name, String sec, int depID, int sem) {
        this.USN = USN;
        this.name = name;
        this.sec = sec;
        this.depID = depID;
        this.sem = sem;
    }

    public Student() {
    }

    public String getUSN() {
        return USN;
    }

    public String getName() {
        return name;
    }

    public String getSec() {
        return sec;
    }

    public int getDepID() {
        return depID;
    }

    public int getSem() {
        return sem;
    }
}
