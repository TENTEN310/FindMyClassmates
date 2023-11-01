package com.hfad.classmates.objectClasses;

import com.google.firebase.Timestamp;

public class Post{
    String postContent;
    Timestamp timestamp;
    String userID;
    String userName;
    int likes;

    public Post(){}
    public Post(String postContent, Timestamp timestamp, String userID, int likes, String userName) {
        this.postContent = postContent;
        this.timestamp = timestamp;
        this.userID = userID;
        this.likes = likes;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}