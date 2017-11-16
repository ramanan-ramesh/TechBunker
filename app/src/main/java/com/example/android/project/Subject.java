package com.example.android.project;

import java.util.List;

/**
 * Created by ramanan_ramesh on 24-Sep-17.
 */

public class Subject {

    int credits, numberOfHours;
    String subCode, subName;
    List<String> content;

    public Subject() {
    }

    public Subject(int credits, int numberOfHours, String subName, String subCode) {
        this.credits = credits;
        this.numberOfHours = numberOfHours;
        this.subName = subName;
        this.subCode = subCode;
    }

    public String getSubCode() {
        return subCode;
    }

    public int getCredits() {
        return credits;
    }

    public int getNumberOfHours() {
        return numberOfHours;
    }

    public String getSubName() {
        return subName;
    }

    public List<String> getContent() {
        return content;
    }
}
