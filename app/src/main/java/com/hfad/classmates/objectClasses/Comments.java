package com.hfad.classmates.objectClasses;

import com.google.firebase.Timestamp;

public class Comments {
    private String message;
    private String senderId;
    private Timestamp timestamp;
    private String className;
    private double overall, workload;
    private boolean attendance, late;

    public Comments() {
    }

    public Comments(String message, String senderId, Timestamp timestamp, String className, double overall, double workload
    , boolean attendance, boolean late) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.className = className;
        this.overall = overall;
        this.workload = workload;
        this.attendance = attendance;
        this.late = late;
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

    public void setOverall(double rating) {
        this.overall = overall;
    }

    public double getWorkload() {
        return workload;
    }

    public void setWorkload(double workload) {this.workload = workload;}
}
