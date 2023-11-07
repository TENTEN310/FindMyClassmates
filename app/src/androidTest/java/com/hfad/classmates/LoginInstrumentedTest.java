package com.hfad.classmates;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import com.hfad.classmates.regLogInActivity.Login;

import org.junit.Rule;
import org.junit.Test;

public class LoginInstrumentedTest {
    @Rule
    public ActivityScenarioRule<Login> activityScenarioRule = new ActivityScenarioRule<>(Login.class);

    @Test
    public void testLoginSuccess() {
        Espresso.onView(ViewMatchers.withId(R.id.SigninEmail))
                .perform(ViewActions.typeText("druzhini@usc.edu"));

        Espresso.onView(ViewMatchers.withId(R.id.SigninPass))
                .perform(ViewActions.typeText("testing"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
                .perform(ViewActions.click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.activity_main))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testLoginFail() {
        Espresso.onView(ViewMatchers.withId(R.id.SigninEmail))
                .perform(ViewActions.typeText("testing@usc.edu"));

        Espresso.onView(ViewMatchers.withId(R.id.SigninPass))
                .perform(ViewActions.typeText("testing"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
                .perform(ViewActions.click());

        // logging in may take a while
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // if we failed to log in, the login button should still be visible
        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testLoginNoEmail() {

        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.SigninEmail))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Please enter email")));
    }

    @Test
    public void testLoginNoPassword() {
        Espresso.onView(ViewMatchers.withId(R.id.SigninEmail))
                .perform(ViewActions.typeText("druzhini@usc.edu"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.SigninPass))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Please enter password")));
    }
}
