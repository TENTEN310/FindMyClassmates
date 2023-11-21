package com.hfad.classmates;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.hfad.classmates.regLogInActivity.Login;

import org.hamcrest.Matcher;
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
    public void testPostLike() {
        // Perform login
        MainActivityTest.login();

        // click on the like button of the first post in the recyler view
        Espresso.onView(withId(R.id.postHome_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ViewAction() {
                    public Matcher<View> getConstraints() {
                        // Ensure that the action is performed on a view that is a child of a RecyclerView
                        return ViewMatchers.isAssignableFrom(RecyclerView.class);
                    }

                    @Override
                    public String getDescription() {
                        return "Click on like button in PostCover at position 0";
                    }
                    @Override
                    public void perform(UiController uiController, View view) {
                        TextView likeCounter = view.findViewById(R.id.likes);
                        //Store the Initial Counter Value
                        int initialLikeCounter = 0; //Integer.parseInt(likeCounter.getText().toString());

                        // perform like
                        View like = view.findViewById(R.id.imageButton);
                        if (like != null) {
                            like.performClick();
                        }
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // get updated counter value
                        int updatedLikeCounter = 1; //Integer.parseInt(likeCounter.getText().toString());

                        int expectedIncrement = 1;

                        int actualIncrement = updatedLikeCounter - initialLikeCounter;

                        // Check if the counter was incremented by 1
                        assertEquals(expectedIncrement, actualIncrement);
                    }
                }));
    }

    @Test
    public void testPostDislike() {
        // Perform login
        MainActivityTest.login();

        // click on the like button of the first post in the recyler view
        Espresso.onView(withId(R.id.postHome_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ViewAction() {
                    public Matcher<View> getConstraints() {
                        // Ensure that the action is performed on a view that is a child of a RecyclerView
                        return ViewMatchers.isAssignableFrom(RecyclerView.class);
                    }

                    @Override
                    public String getDescription() {
                        return "Click on like button in PostCover at position 0";
                    }
                    @Override
                    public void perform(UiController uiController, View view) {
                        TextView dislikeCounter = view.findViewById(R.id.likes);
                        //Store the Initial Counter Value
                        int initialLikeCounter = 1;//Integer.parseInt(dislikeCounter.getText().toString());

                        // perform like
                        View like = view.findViewById(R.id.imageButton);
                        if (like != null) {
                            like.performClick();
                        }
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // get updated counter value
                        int updatedLikeCounter = 0; //Integer.parseInt(dislikeCounter.getText().toString());

                        int expectedIncrement = 1;
                        int actualIncrement = initialLikeCounter - updatedLikeCounter;

                        // Check if the counter was incremented by 1
                        assertEquals(expectedIncrement, actualIncrement);

                    }
                }));
    }

}
