package com.hfad.classmates;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    //create a testing firebase user, that way the tests will work

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testChatsFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.nav_chats)).perform(ViewActions.click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.fragment_chats)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testClassesFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.nav_classes)).perform(ViewActions.click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.fragment_classes)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testHomeFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.nav_home)).perform(ViewActions.click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.fragment_home)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testPostFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.nav_post)).perform(ViewActions.click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.fragment_post)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testProfileFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.nav_profile)).perform(ViewActions.click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.fragment_profile)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
