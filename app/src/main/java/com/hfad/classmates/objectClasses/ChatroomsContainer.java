package com.hfad.classmates.objectClasses;

import com.google.firebase.Timestamp;

import java.util.List;

public class ChatroomsContainer {
    String chatroomID;
    List<String> userIDs;
    List<ChatsContainer> messages;
    Timestamp lastTimestamp;
    String lastMessageUserID;
}
