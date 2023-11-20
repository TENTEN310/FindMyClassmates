package com.hfad.classmates;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.hfad.classmates.regLogInActivity.Login;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
public class ProfileTest {
    @Rule
    public ActivityScenarioRule<Login> activityScenarioRule = new ActivityScenarioRule<>(Login.class);

//    @Test
//    public void testNameChange() {
//        // Perform login
//        MainActivityTest.login();
//        // go the profile page
//        Espresso.onView(ViewMatchers.withId(R.id.nav_profile)).perform(ViewActions.click());
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // click on the edit button
//        Espresso.onView(withId(R.id.changeUsername)).perform(ViewActions.click());
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // change the name
//        Espresso.onView(withId(R.id.usernameText)).perform(ViewActions.typeText("amyTest"), ViewActions.closeSoftKeyboard());
//
//
//    }
}
