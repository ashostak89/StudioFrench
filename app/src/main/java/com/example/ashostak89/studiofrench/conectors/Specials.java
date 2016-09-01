package com.example.ashostak89.studiofrench.conectors;

/**
 * Created by ashostak89 on 8/27/2016.
 */
public class Specials {
    private String specialName;
    private String specialInfo;

    public Specials(String specialName, String specialInfo) {
        this.specialName = specialName;
        this.specialInfo = specialInfo;
    }

    public void setSpecialInfo(String specialInfo) {
        this.specialInfo = specialInfo;
    }

    public String getSpecialName() {

        return specialName;
    }

    public String getSpecialInfo() {
        return specialInfo;
    }
}
