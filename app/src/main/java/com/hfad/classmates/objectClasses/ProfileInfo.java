package com.hfad.classmates.objectClasses;

import com.google.firebase.Timestamp;

import java.util.List;

public class ProfileInfo {
    public ProfileInfo() {}
    public ProfileInfo(String username, String year, String major, String school, String USCID, Timestamp timestamp, String userID) {
        this.username = username;
        this.year = year;
        this.major = major;
        this.school = school;
        this.timestamp = timestamp;
        this.userID = userID;
        this.USCID = USCID;
    }

    public List<String> getBlockList() {
        return blockList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUSCID() {
        return USCID;
    }

    public void setUSCID(String USCID) {
        this.USCID = USCID;
    }

    private String username;
    private String year;

    private String major;
    private String school;
    private Timestamp timestamp;
    private String USCID;

    private String userID;
    private List<String> blockList;

}
