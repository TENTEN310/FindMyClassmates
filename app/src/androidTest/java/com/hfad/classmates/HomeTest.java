package com.hfad.classmates;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

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

public class HomeTest {
    @Rule
    public ActivityScenarioRule<Login> activityScenarioRule = new ActivityScenarioRule<>(Login.class);

    @Test
    public void testUserProfilePopup() {
        // Perform login
        MainActivityTest.login();
        Espresso.onView(withId(R.id.nav_home)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(withId(R.id.postHome_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ViewAction() {
                    public Matcher<View> getConstraints() {
                        // Ensure that the action is performed on a view that is a child of a RecyclerView
                        return ViewMatchers.isAssignableFrom(RecyclerView.class);
                    }

                    @Override
                    public String getDescription() {
                        return "Click on profile_pic_image_view in PostCover at position 0";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        // Find the child view (profile_pic_image_view) within the PostCover item
                        View profilePicImageView = view.findViewById(R.id.profile_pic_image_view);

                        // Perform a click action on profile_pic_image_view
                        if (profilePicImageView != null) {
                            profilePicImageView.performClick();
                        }
                    }
                }));

        // Check if the userPopup is displayed
        Espresso.onView(withId(R.id.userPopup))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }

    @Test
    public void testUserChatPopup() {
        // Perform login
        MainActivityTest.login();
        Espresso.onView(withId(R.id.nav_home)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(withId(R.id.postHome_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ViewAction() {
                    public Matcher<View> getConstraints() {
                        // Ensure that the action is performed on a view that is a child of a RecyclerView
                        return ViewMatchers.isAssignableFrom(RecyclerView.class);
                    }

                    @Override
                    public String getDescription() {
                        return "Click on profile_pic_image_view in PostCover at position 0";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        // Find the child view (profile_pic_image_view) within the PostCover item
                        View profilePicImageView = view.findViewById(R.id.profile_pic_image_view);

                        // Perform a click action on profile_pic_image_view
                        if (profilePicImageView != null) {
                            profilePicImageView.performClick();
                        }
                    }
                }));

        // Click on the "Message" button inside the userPopup to message the user
        Espresso.onView(withId(R.id.Message))
                .perform(ViewActions.click());

        // Check if the activity_inside_chat where chat details is displayed
        Espresso.onView(withId(R.id.activity_inside_chat))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


}
