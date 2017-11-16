package com.example.android.project.techbunker;

/**
 * Created by ramanan_ramesh on 06-Nov-17.
 */

public class TechBunker {
    String subName;
    int bunks, classes, minPerscentage;
    float percentage;
    float ratio;

    public TechBunker(){

    }

    public TechBunker(String subName, int bunks, int classes, float percentage, int minPerscentage, float ratio) {
        this.subName = subName;
        this.bunks = bunks;
        this.classes = classes;
        this.percentage = percentage;
        this.minPerscentage = minPerscentage;
        this.ratio = ratio;
    }

    public String getSubName() {
        return subName;
    }

    public int getBunks() {
        return bunks;
    }

    public int getClasses() {
        return classes;
    }

    public float getPercentage() {
        return percentage;
    }

    public int getMinPerscentage() {
        return minPerscentage;
    }

    public float getRatio() {
        return ratio;
    }
}

