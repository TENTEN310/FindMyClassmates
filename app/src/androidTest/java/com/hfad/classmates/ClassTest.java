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

public class ClassTest {
    @Rule
    public ActivityScenarioRule<Login> activityScenarioRule = new ActivityScenarioRule<>(Login.class);

    @Test
    public void testClass() {
        // Perform login
        MainActivityTest.login();
        // go the class page
        Espresso.onView(ViewMatchers.withId(R.id.nav_classes)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // click on the first department in the recycler view
        Espresso.onView(withId(R.id.post_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        // click on the first class in the recycler view to view the class detail page
        Espresso.onView(withId(R.id.post_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        //back to class page
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.onView(withId(R.id.nav_classes))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
