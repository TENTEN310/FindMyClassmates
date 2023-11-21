package com.hfad.classmates;

import static org.junit.Assert.assertEquals;

import com.google.firebase.Timestamp;
import com.hfad.classmates.objectClasses.Comments;

import org.junit.Test;

public class CommentTest {
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
}
