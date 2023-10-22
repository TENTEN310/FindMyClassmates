package com.hfad.classmates.UserProfile;

import com.google.firebase.Timestamp;

public class ProfileInfo {
    public ProfileInfo() {}
    public ProfileInfo(String username, String year, String major, String school, Timestamp timestamp, String userID) {
        this.username = username;
        this.year = year;
        this.major = major;
        this.school = school;
        this.timestamp = timestamp;
        this.userID = userID;
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

    private String username;
    private String year;

    private String major;
    private String school;
    private Timestamp timestamp;

    private String userID;

}
