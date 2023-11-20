

package com.hfad.classmates;

import com.google.firebase.Timestamp;
import com.hfad.classmates.objectClasses.ChatroomsContainer;
import com.hfad.classmates.objectClasses.ChatsContainer;
import com.hfad.classmates.objectClasses.Classes;
import com.hfad.classmates.objectClasses.Comments;
import com.hfad.classmates.objectClasses.Dept;
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
    public void testChatRoomConstructor() {
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

    @Test
    public void testMessage() {
        String message = "Hello Chat!";
        ChatsContainer chatsContainer = new ChatsContainer();
        chatsContainer.setMessage(message);
        assertEquals(message, chatsContainer.getMessage());
    }

    @Test
    public void testSenderId() {
        String senderId = "user123";
        ChatsContainer chatsContainer = new ChatsContainer();
        chatsContainer.setSenderId(senderId);
        assertEquals(senderId, chatsContainer.getSenderId());
    }

    @Test
    public void testTimestamp() {
        Timestamp timestamp = Timestamp.now(); // Adjust according to your Timestamp implementation
        ChatsContainer chatsContainer = new ChatsContainer();
        chatsContainer.setTimestamp(timestamp);
        assertEquals(timestamp, chatsContainer.getTimestamp());
    }

    @Test
    public void testChatsConstructor() {
        String message = "Hello Chat!";
        String senderId = "user123";
        Timestamp timestamp = Timestamp.now(); // Adjust according to your Timestamp implementation

        ChatsContainer chatsContainer = new ChatsContainer(message, senderId, timestamp);
        assertEquals(message, chatsContainer.getMessage());
        assertEquals(senderId, chatsContainer.getSenderId());
        assertEquals(timestamp, chatsContainer.getTimestamp());
    }

    @Test
    public void testAbv() {
        String abv = "CS101";
        Classes classes = new Classes();
        classes.setAbv(abv);
        assertEquals(abv, classes.getAbv());
    }

    @Test
    public void testDescription() {
        String description = "Introduction to Computer Science";
        Classes classes = new Classes();
        classes.setDescription(description);
        assertEquals(description, classes.getDescription());
    }

    @Test
    public void testName() {
        String name = "Computer Science I";
        Classes classes = new Classes();
        classes.setName(name);
        assertEquals(name, classes.getName());
    }

    @Test
    public void testProfessor() {
        String professor = "Dr. Smith";
        Classes classes = new Classes();
        classes.setProfessor(professor);
        assertEquals(professor, classes.getProfessor());
    }

    @Test
    public void testTerm() {
        String term = "Fall 2023";
        Classes classes = new Classes();
        classes.setTerm(term);
        assertEquals(term, classes.getTerm());
    }

    @Test
    public void testUnits() {
        int units = 4;
        Classes classes = new Classes();
        classes.setUnits(units);
        assertEquals(units, classes.getUnits());
    }

    @Test
    public void testClassesConstructor() {
        String description = "Introduction to Computer Science";
        String name = "Computer Science I";
        String professor = "Dr. Smith";
        String term = "Fall 2023";
        int units = 4;
        String abv = "CS101";

        Classes classes = new Classes(description, name, professor, term, units, abv);
        assertEquals(description, classes.getDescription());
        assertEquals(name, classes.getName());
        assertEquals(professor, classes.getProfessor());
        assertEquals(term, classes.getTerm());
        assertEquals(units, classes.getUnits());
        assertEquals(abv, classes.getAbv());
    }

    @Test
    public void testClassesMessage() {
        String message = "Nice class!";
        Comments comment = new Comments();
        comment.setMessage(message);
        assertEquals(message, comment.getMessage());
    }

    @Test
    public void testClassesSenderId() {
        String senderId = "user123";
        Comments comment = new Comments();
        comment.setSenderId(senderId);
        assertEquals(senderId, comment.getSenderId());
    }

    @Test
    public void testClassesTimestamp() {
        Timestamp timestamp = Timestamp.now(); // Adjust according to your Timestamp implementation
        Comments comment = new Comments();
        comment.setTimestamp(timestamp);
        assertEquals(timestamp, comment.getTimestamp());
    }

    @Test
    public void testClassName() {
        String className = "CS101";
        Comments comment = new Comments();
        comment.setClassName(className);
        assertEquals(className, comment.getClassName());
    }

    @Test
    public void testOverall() {
        double overall = 4.5;
        Comments comment = new Comments();
        comment.setOverall(overall);
        assertEquals(overall, comment.getOverall(), 0.0);
    }

    @Test
    public void testWorkload() {
        double workload = 3.5;
        Comments comment = new Comments();
        comment.setWorkload(workload);
        assertEquals(workload, comment.getWorkload(), 0.0);
    }

    @Test
    public void testAttendance() {
        boolean attendance = true;
        Comments comment = new Comments();
        comment.setAttendance(attendance);
        assertEquals(attendance, comment.getAttendance());
    }

    @Test
    public void testLate() {
        boolean late = false;
        Comments comment = new Comments();
        comment.setLate(late);
        assertEquals(late, comment.getLate());
    }

    @Test
    public void testLikes() {
        int likes = 10;
        Comments comment = new Comments();
        comment.setLikes(likes);
        assertEquals(likes, comment.getLikes());
    }

    @Test
    public void testDislikes() {
        int dislikes = 2;
        Comments comment = new Comments();
        comment.setDislikes(dislikes);
        assertEquals(dislikes, comment.getDislikes());
    }

    @Test
    public void testUid() {
        String uid = "UID123";
        Comments comment = new Comments();
        comment.setUid(uid);
        assertEquals(uid, comment.getUid());
    }

    @Test
    public void testCommentsConstructor() {
        String message = "Nice class!";
        String senderId = "user123";
        Timestamp timestamp = Timestamp.now(); // Adjust according to your Timestamp implementation
        String className = "CS101";
        double overall = 4.5;
        double workload = 3.5;
        boolean attendance = true;
        boolean late = false;
        int likes = 10;
        int dislikes = 2;
        String uid = "UID123";

        Comments comment = new Comments(message, senderId, timestamp, className, overall, workload, attendance, late, likes, dislikes, uid);
        assertEquals(message, comment.getMessage());
        assertEquals(senderId, comment.getSenderId());
        assertEquals(timestamp, comment.getTimestamp());
        assertEquals(className, comment.getClassName());
        assertEquals(overall, comment.getOverall(), 0.0);
        assertEquals(workload, comment.getWorkload(), 0.0);
        assertEquals(attendance, comment.getAttendance());
        assertEquals(late, comment.getLate());
        assertEquals(likes, comment.getLikes());
        assertEquals(dislikes, comment.getDislikes());
        assertEquals(uid, comment.getUid());
    }

    @Test
    public void testDepartmentName() {
        String departmentName = "Computer Science";
        Dept dept = new Dept();
        dept.setDepartmentName(departmentName);
        assertEquals(departmentName, dept.getDepartmentName());
    }

    @Test
    public void testDeptAbv() {
        String abv = "CS";
        Dept dept = new Dept();
        dept.setAbv(abv);
        assertEquals(abv, dept.getAbv());
    }

    @Test
    public void testDeptConstructor() {
        String departmentName = "Computer Science";
        String abv = "CS";
        Dept dept = new Dept(departmentName, abv);
        assertEquals(departmentName, dept.getDepartmentName());
        assertEquals(abv, dept.getAbv());
    }
}