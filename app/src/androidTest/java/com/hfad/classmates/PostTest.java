package com.hfad.classmates;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.hfad.classmates.regLogInActivity.Login;

import org.junit.Rule;
import org.junit.Test;

public class PostTest {
    @Rule
    public ActivityScenarioRule<Login> activityScenarioRule = new ActivityScenarioRule<>(Login.class);

    @Test
    public void testPost() {
        // Perform login
        Espresso.onView(ViewMatchers.withId(R.id.SigninEmail))
                .perform(ViewActions.typeText("druzhini@usc.edu"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.SigninPass))
                .perform(ViewActions.typeText("testing"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
                .perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // navigate to the posting page
        Espresso.onView(ViewMatchers.withId(R.id.nav_post)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Perform writing description for post
        Espresso.onView(ViewMatchers.withId(R.id.post))
                .perform(ViewActions.typeText("this is the post the test function1"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.upload))
                .perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // after posting, user should be redirected to the home page
        Espresso.onView(ViewMatchers.withId(R.id.nav_home))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testPostNoDescription() {
        // Perform login
        MainActivityTest.login();
        Espresso.onView(ViewMatchers.withId(R.id.nav_post)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Perform writing description for post
        Espresso.onView(ViewMatchers.withId(R.id.post))
                .perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.upload))
                .perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // if no description, the post button should still be visible
        Espresso.onView(ViewMatchers.withId(R.id.upload)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testPostResult() {
        // Perform login
        MainActivityTest.login();
        Espresso.onView(ViewMatchers.withId(R.id.nav_post)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // navigate to the posting page
        Espresso.onView(ViewMatchers.withId(R.id.nav_post)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Perform writing description for post
        Espresso.onView(ViewMatchers.withId(R.id.post))
                .perform(ViewActions.typeText("this is the post the test function1"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.upload))
                .perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // after posting, user should be redirected to the home page
        // check recycler view on home page
        Espresso.onView(ViewMatchers.withId(R.id.post_recycler_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
