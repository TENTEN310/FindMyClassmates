package com.hfad.classmates;

import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.util.regex.Pattern.matches;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.hfad.classmates.regLogInActivity.Login;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ChatNavigationTest {

    @Rule
    public ActivityScenarioRule<Login> loginActivityRule = new ActivityScenarioRule<>(Login.class);

    @Before
    public void init() {
        Intents.init();
    }

    @Test
    public void testLoginAndNavigateChat() {
        // Perform login
        MainActivityTest.login();
        Espresso.onView(withId(R.id.nav_chats)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(withId(R.id.fragment_chats))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSearchChatPage() {
        // Perform login
        MainActivityTest.login();
        Espresso.onView(withId(R.id.nav_chats)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(withId(R.id.chat_search_btn))
                .perform(ViewActions.click());
        Espresso.onView(withId(R.id.activity_contact_search))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSearchUserForChat() {
        // Perform login
        MainActivityTest.login();
        Espresso.onView(withId(R.id.nav_chats)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(withId(R.id.chat_search_btn))
                .perform(ViewActions.click());
        Espresso.onView(withId(R.id.seach_username_input))
                .perform(ViewActions.typeText("sidqian"), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.search_user_btn))
                .perform(ViewActions.click());
        //check the first recycler view item
        Espresso.onView(withId(R.id.search_user_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        Espresso.onView(withId(R.id.activity_inside_chat))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


    @Test
    public void TestSendMessageAndBackHome() {
        // Perform login
        MainActivityTest.login();
        Espresso.onView(withId(R.id.nav_chats)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(withId(R.id.chat_search_btn))
                .perform(ViewActions.click());
        Espresso.onView(withId(R.id.seach_username_input))
                .perform(ViewActions.typeText("sidqian"), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.search_user_btn))
                .perform(ViewActions.click());
        //check the first recycler view item
        Espresso.onView(withId(R.id.search_user_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        Espresso.onView(withId(R.id.chat_message_input))
                .perform(ViewActions.typeText("test"), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.message_send_btn))
                .perform(ViewActions.click());
        //back to home
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.onView(withId(R.id.activity_main))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void TestSendMessageByHistory() {
        // Perform login
        MainActivityTest.login();
        Espresso.onView(withId(R.id.nav_chats)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.search_user_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        Espresso.onView(withId(R.id.chat_message_input))
                .perform(ViewActions.typeText("test"), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.message_send_btn))
                .perform(ViewActions.click());

        // get the last item in the recycler view and make sure the text is "test"
        Espresso.onView(withId(R.id.chat_recycler_view))
                .check(ViewAssertions.matches(ViewMatchers.hasDescendant(withText("test"))));
    }




    @After
    public void tearDown() {
        Intents.release();
    }
}
