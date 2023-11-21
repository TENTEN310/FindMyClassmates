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

    @Test
    public void testAddClass() {
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
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));

        // click on the first class in the recycler view to view the class detail page
        Espresso.onView(withId(R.id.post_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        // click on the add button
        Espresso.onView(withId(R.id.imageButton2)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //navigate to the roster page by clicking on roster button
        Espresso.onView(withId(R.id.button)).perform(ViewActions.click());

        // check the recycler view
        Espresso.onView(withId(R.id.post_recycler_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testRemoveClass() {
        // Perform login
        MainActivityTest.login();
        // go to the profile page
        Espresso.onView(ViewMatchers.withId(R.id.nav_profile)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // click on the first class in the class list recycler view
        Espresso.onView(withId(R.id.itemsList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        // remove yourself from the class currently enrolled in
        Espresso.onView(withId(R.id.imageButton4)).perform(ViewActions.click());
        Espresso.pressBack();
        // check if the class is removed
        Espresso.onView(withId(R.id.itemsList))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testAddReview(){
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

        // Scroll to the "Comment" button
        Espresso.onView(withId(R.id.commentbtn)).perform(ViewActions.scrollTo());

        // click on the add review button
        Espresso.onView(withId(R.id.commentbtn)).perform(ViewActions.click());

        // write review on the reviewPage
        Espresso.onView(withId(R.id.Review)).perform(ViewActions.typeText("testing review"), ViewActions.closeSoftKeyboard());

        // click on the post button
        Espresso.onView(withId(R.id.Message)).perform(ViewActions.click());

        // make sure the class detail page is still displayed
        Espresso.onView(withId(R.id.classDetailPage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testAddEmptyReview(){
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

        // Scroll to the "Comment" button
        Espresso.onView(withId(R.id.commentbtn)).perform(ViewActions.scrollTo());

        // click on the add review button
        Espresso.onView(withId(R.id.commentbtn)).perform(ViewActions.click());

        // write review on the reviewPage
        Espresso.onView(withId(R.id.Review)).perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard());

        // click on the post button
        Espresso.onView(withId(R.id.Message)).perform(ViewActions.click());

        // user should still see the review page
        Espresso.onView(withId(R.id.reviewPage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
