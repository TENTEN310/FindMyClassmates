

package com.hfad.classmates;

import com.google.firebase.Timestamp;
import com.hfad.classmates.objectClasses.ChatroomsContainer;
import com.hfad.classmates.util.FirebaseUtil;

import android.content.Intent;
import com.hfad.classmates.objectClasses.ProfileInfo;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class ObjectsTest {
    @Test
    public void testChatroomID() {
        String chatroomID = "chat123";
        ChatroomsContainer container = new ChatroomsContainer();
        container.setChatroomID(chatroomID);
        assertEquals(chatroomID, container.getChatroomID());
    }

    @Test
    public void testUserIDs() {
        List<String> userIDs = Arrays.asList("user1", "user2");
        ChatroomsContainer container = new ChatroomsContainer();
        container.setUserIDs(userIDs);
        assertEquals(userIDs, container.getUserIDs());
    }

    @Test
    public void testLastTimestamp() {
        Timestamp timestamp = Timestamp.now(); // You might need to adjust this depending on how Timestamp is defined
        ChatroomsContainer container = new ChatroomsContainer();
        container.setLastTimestamp(timestamp);
        assertEquals(timestamp, container.getLastTimestamp());
    }

    @Test
    public void testLastMessageUserID() {
        String lastMessageUserID = "user123";
        ChatroomsContainer container = new ChatroomsContainer();
        container.setLastMessageUserID(lastMessageUserID);
        assertEquals(lastMessageUserID, container.getLastMessageUserID());
    }

    @Test
    public void testLastMessage() {
        String lastMessage = "Hello, world!";
        ChatroomsContainer container = new ChatroomsContainer();
        container.setLastMessage(lastMessage);
        assertEquals(lastMessage, container.getLastMessage());
    }

    @Test
    public void testConstructor() {
        String chatroomID = "chat123";
        List<String> userIDs = Arrays.asList("user1", "user2");
        Timestamp timestamp = Timestamp.now();
        String lastMessageUserID = "user123";
        String lastMessage = "Hello, world!";

        ChatroomsContainer container = new ChatroomsContainer(chatroomID, userIDs, timestamp, lastMessageUserID, lastMessage);
        assertEquals(chatroomID, container.getChatroomID());
        assertEquals(userIDs, container.getUserIDs());
        assertEquals(timestamp, container.getLastTimestamp());
        assertEquals(lastMessageUserID, container.getLastMessageUserID());
        assertEquals(lastMessage, container.getLastMessage());
    }

}