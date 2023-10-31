package com.hfad.classmates.objectClasses;

import com.google.firebase.Timestamp;


public class Comments {
    private String message;
    private String senderId;
    private Timestamp timestamp;
    private String className, uid;
    private double overall;
    private double workload;
    private boolean attendance, late;
    private int likes, dislikes;

    public Comments() {
    }

    public Comments(String message, String senderId, Timestamp timestamp, String className, double overall, double workload
    , boolean attendance, boolean late, int likes, int dislikes, String uid) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.className = className;
        this.overall = overall;
        this.workload = workload;
        this.attendance = attendance;
        this.late = late;
        this.likes = likes;
        this.dislikes = dislikes;
        this.uid = uid;
    }

    public boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }

    public boolean getLate() {
        return late;
    }

    public void setLate(boolean late) {
        this.late = late;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getOverall() {
        return overall;
    }

    public void setOverall(double overall) {
        this.overall = overall;
    }

    public double getWorkload() {
        return workload;
    }

    public void setWorkload(double workload) {this.workload = workload;}

    public int getLikes() {return likes;}

    public void setLikes(int likes) {this.likes = likes;}

    public int getDislikes() {return dislikes;}

    public void setDislikes(int dislikes) {this.dislikes = dislikes;}

    public String getUid() {return uid;}

    public void setUid(String uid) {this.uid = uid;}
}
