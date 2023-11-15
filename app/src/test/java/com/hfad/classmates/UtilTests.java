

package com.hfad.classmates;

import com.google.firebase.Timestamp;
import com.hfad.classmates.util.FirebaseUtil;

import android.content.Intent;
import com.hfad.classmates.objectClasses.ProfileInfo;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class UtilTests {

    private String formatTestTimestamp(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        Date testDate = calendar.getTime();
        Timestamp testTimestamp = new Timestamp(testDate);

        return FirebaseUtil.reformateTime(testTimestamp);
    }

    private String expectedFormattedTime(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(calendar.getTime());
    }

    @Test
    public void reformateTime_handlesMidnight() {
        String formattedTime = formatTestTimestamp(2020, Calendar.JANUARY, 1, 0, 0);
        String expectedTime = expectedFormattedTime(2020, Calendar.JANUARY, 1, 0, 0);
        assertEquals(expectedTime, formattedTime);
    }

    @Test
    public void reformateTime_handlesNoon() {
        String formattedTime = formatTestTimestamp(2020, Calendar.JANUARY, 1, 12, 0);
        String expectedTime = expectedFormattedTime(2020, Calendar.JANUARY, 1, 12, 0);
        assertEquals(expectedTime, formattedTime);
    }

    @Test
    public void reformateTime_handlesAfternoon() {
        String formattedTime = formatTestTimestamp(2020, Calendar.JANUARY, 1, 15, 30);
        String expectedTime = expectedFormattedTime(2020, Calendar.JANUARY, 1, 15, 30);
        assertEquals(expectedTime, formattedTime);
    }

    @Test
    public void reformateTime_handlesEvening() {
        String formattedTime = formatTestTimestamp(2020, Calendar.JANUARY, 1, 19, 45);
        String expectedTime = expectedFormattedTime(2020, Calendar.JANUARY, 1, 19, 45);
        assertEquals(expectedTime, formattedTime);
    }

    @Test
    public void reformateTime_handlesLateNight() {
        String formattedTime = formatTestTimestamp(2020, Calendar.JANUARY, 1, 23, 59);
        String expectedTime = expectedFormattedTime(2020, Calendar.JANUARY, 1, 23, 59);
        assertEquals(expectedTime, formattedTime);
    }

    @Test
    public void getDeptFromClassID_WithSimpleCase() {
        String classID = "CSCI101";
        String expectedDept = "CSCI";
        assertEquals(expectedDept, FirebaseUtil.GetDeptFromClassID(classID));
    }

    @Test
    public void getDeptFromClassID_WithComplexCase() {
        String classID = "ENGL202A";
        String expectedDept = "ENGL";
        assertEquals(expectedDept, FirebaseUtil.GetDeptFromClassID(classID));
    }

    @Test
    public void getDeptFromClassID_WithNoNumbers() {
        String classID = "HIST";
        String expectedDept = "HIST";
        assertEquals(expectedDept, FirebaseUtil.GetDeptFromClassID(classID));
    }

    @Test
    public void getDeptFromClassID_WithEmptyString() {
        String classID = "";
        String expectedDept = "";
        assertEquals(expectedDept, FirebaseUtil.GetDeptFromClassID(classID));
    }

    @Test
    public void getDeptFromClassID_WithNumbersOnly() {
        String classID = "1234";
        String expectedDept = "";
        assertEquals(expectedDept, FirebaseUtil.GetDeptFromClassID(classID));
    }

    private String formatTestTimestampToYear(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        Date testDate = calendar.getTime();
        Timestamp testTimestamp = new Timestamp(testDate);

        return FirebaseUtil.reformateDateAndTime(testTimestamp);
    }

    private String expectedFormattedDateTime(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(calendar.getTime());
    }

    @Test
    public void reformateDateAndTime_handlesTypicalCase() {
        String formattedDateTime = formatTestTimestampToYear(2020, Calendar.APRIL, 15, 10, 30);
        String expectedDateTime = expectedFormattedDateTime(2020, Calendar.APRIL, 15, 10, 30);
        assertEquals(expectedDateTime, formattedDateTime);
    }

    @Test
    public void reformateDateAndTime_handlesEndOfMonth() {
        String formattedDateTime = formatTestTimestampToYear(2020, Calendar.MARCH, 31, 23, 59);
        String expectedDateTime = expectedFormattedDateTime(2020, Calendar.MARCH, 31, 23, 59);
        assertEquals(expectedDateTime, formattedDateTime);
    }

    @Test
    public void reformateDateAndTime_handlesLeapYear() {
        String formattedDateTime = formatTestTimestampToYear(2020, Calendar.FEBRUARY, 29, 12, 0);
        String expectedDateTime = expectedFormattedDateTime(2020, Calendar.FEBRUARY, 29, 12, 0);
        assertEquals(expectedDateTime, formattedDateTime);
    }

    @Test
    public void reformateDateAndTime_handlesNewYear() {
        String formattedDateTime = formatTestTimestampToYear(2021, Calendar.JANUARY, 1, 0, 0);
        String expectedDateTime = expectedFormattedDateTime(2021, Calendar.JANUARY, 1, 0, 0);
        assertEquals(expectedDateTime, formattedDateTime);
    }

    @Test
    public void reformateDateAndTime_handlesNoon() {
        String formattedDateTime = formatTestTimestampToYear(2020, Calendar.JULY, 4, 12, 0);
        String expectedDateTime = expectedFormattedDateTime(2020, Calendar.JULY, 4, 12, 0);
        assertEquals(expectedDateTime, formattedDateTime);
    }
}

