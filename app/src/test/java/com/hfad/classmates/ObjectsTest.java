

package com.hfad.classmates;

import com.google.firebase.Timestamp;
import com.hfad.classmates.objectClasses.ChatroomsContainer;
import com.hfad.classmates.objectClasses.ChatsContainer;
import com.hfad.classmates.objectClasses.Classes;
import com.hfad.classmates.objectClasses.Comments;
import com.hfad.classmates.objectClasses.Dept;
import com.hfad.classmates.objectClasses.Materials;
import com.hfad.classmates.objectClasses.Post;
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

    @Test
    public void testMaterialConstructorAndGetters() {
        String materialName = "Lecture Notes";
        String fileType = "pdf";
        String filePath = "/path/to/lecture/notes.pdf";

        Materials materials = new Materials(materialName, fileType, filePath);

        assertEquals(materialName, materials.getMaterialName());
        assertEquals(fileType, materials.getFileType());
        assertEquals(filePath, materials.getFilePath());
    }

    @Test
    public void testUserName() {
        String userName = "John Doe";
        Post post = new Post();
        post.setUserName(userName);
        assertEquals(userName, post.getUserName());
    }

    @Test
    public void testPostContent() {
        String postContent = "This is a sample post content.";
        Post post = new Post();
        post.setPostContent(postContent);
        assertEquals(postContent, post.getPostContent());
    }

    @Test
    public void testPostTimestamp() {
        Timestamp timestamp = Timestamp.now(); // Adjust according to your Timestamp implementation
        Post post = new Post();
        post.setTimestamp(timestamp);
        assertEquals(timestamp, post.getTimestamp());
    }

    @Test
    public void testUserID() {
        String userID = "user123";
        Post post = new Post();
        post.setUserID(userID);
        assertEquals(userID, post.getUserID());
    }

    @Test
    public void testPostLikes() {
        int likes = 100;
        Post post = new Post();
        post.setLikes(likes);
        assertEquals(likes, post.getLikes());
    }

    @Test
    public void testPostConstructor() {
        String postContent = "This is a sample post content.";
        Timestamp timestamp = Timestamp.now(); // Adjust according to your Timestamp implementation
        String userID = "user123";
        int likes = 100;
        String userName = "John Doe";

        Post post = new Post(postContent, timestamp, userID, likes, userName);
        assertEquals(postContent, post.getPostContent());
        assertEquals(timestamp, post.getTimestamp());
        assertEquals(userID, post.getUserID());
        assertEquals(likes, post.getLikes());
        assertEquals(userName, post.getUserName());
    }


    @Test
    public void testUsername() {
        String username = "JohnDoe";
        ProfileInfo profileInfo = new ProfileInfo();
        profileInfo.setUsername(username);
        assertEquals(username, profileInfo.getUsername());
    }

    @Test
    public void testYear() {
        String year = "Sophomore";
        ProfileInfo profileInfo = new ProfileInfo();
        profileInfo.setYear(year);
        assertEquals(year, profileInfo.getYear());
    }

    @Test
    public void testMajor() {
        String major = "Computer Science";
        ProfileInfo profileInfo = new ProfileInfo();
        profileInfo.setMajor(major);
        assertEquals(major, profileInfo.getMajor());
    }

    @Test
    public void testSchool() {
        String school = "Engineering School";
        ProfileInfo profileInfo = new ProfileInfo();
        profileInfo.setSchool(school);
        assertEquals(school, profileInfo.getSchool());
    }

    @Test
    public void testProfileTimestamp() {
        Timestamp timestamp = Timestamp.now(); // Adjust according to your Timestamp implementation
        ProfileInfo profileInfo = new ProfileInfo();
        profileInfo.setTimestamp(timestamp);
        assertEquals(timestamp, profileInfo.getTimestamp());
    }

    @Test
    public void testProfileUserID() {
        String userID = "user123";
        ProfileInfo profileInfo = new ProfileInfo();
        profileInfo.setUserID(userID);
        assertEquals(userID, profileInfo.getUserID());
    }

    @Test
    public void testUSCID() {
        String USCID = "USC123";
        ProfileInfo profileInfo = new ProfileInfo();
        profileInfo.setUSCID(USCID);
        assertEquals(USCID, profileInfo.getUSCID());
    }

    @Test
    public void testProfileConstructor() {
        String username = "JohnDoe";
        String year = "Sophomore";
        String major = "Computer Science";
        String school = "Engineering School";
        Timestamp timestamp = Timestamp.now(); // Adjust according to your Timestamp implementation
        String userID = "user123";
        String USCID = "USC123";

        ProfileInfo profileInfo = new ProfileInfo(username, year, major, school, USCID, timestamp, userID);
        assertEquals(username, profileInfo.getUsername());
        assertEquals(year, profileInfo.getYear());
        assertEquals(major, profileInfo.getMajor());
        assertEquals(school, profileInfo.getSchool());
        assertEquals(timestamp, profileInfo.getTimestamp());
        assertEquals(userID, profileInfo.getUserID());
        assertEquals(USCID, profileInfo.getUSCID());
    }
}