package com.hfad.classmates.objectClasses;

import com.google.firebase.Timestamp;

import java.util.List;

public class ChatroomsContainer {
    String chatroomID;
    List<String> userIDs;
    Timestamp lastTimestamp;
    String lastMessageUserID;
    String lastMessage;

    public ChatroomsContainer(){}

    public ChatroomsContainer(String chatroomID, List<String> userIDs, Timestamp lastTimestamp, String lastMessageUserID, String lastMessage) {
        this.chatroomID = chatroomID;
        this.userIDs = userIDs;
        this.lastTimestamp = lastTimestamp;
        this.lastMessageUserID = lastMessageUserID;
        this.lastMessage = lastMessage;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getChatroomID() {
        return chatroomID;
    }

    public void setChatroomID(String chatroomID) {
        this.chatroomID = chatroomID;
    }

    public List<String> getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(List<String> userIDs) {
        this.userIDs = userIDs;
    }

    public Timestamp getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(Timestamp lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public String getLastMessageUserID() {
        return lastMessageUserID;
    }

    public void setLastMessageUserID(String lastMessageUserID) {
        this.lastMessageUserID = lastMessageUserID;
    }
}
