package com.hfad.classmates;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import com.hfad.classmates.regLogInActivity.Login;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<Login> loginActivityRule = new ActivityScenarioRule<>(Login.class);

    //login for the tests to successfully run
    public void login() {
        Espresso.onView(ViewMatchers.withId(R.id.SigninEmail))
                .perform(ViewActions.typeText("druzhini@usc.edu"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.SigninPass))
                .perform(ViewActions.typeText("testing"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
                .perform(ViewActions.click());
        //give some time to log in and then continue
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testChatsFragment() {
        login();

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
        login();

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
        login();

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
        login();

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
        login();

        Espresso.onView(ViewMatchers.withId(R.id.nav_profile)).perform(ViewActions.click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.fragment_profile)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
