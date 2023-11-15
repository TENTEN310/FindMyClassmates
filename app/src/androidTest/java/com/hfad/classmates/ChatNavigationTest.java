package com.hfad.classmates;

import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.hfad.classmates.regLogInActivity.Login;
import com.hfad.classmates.chatsActivity.Inside_chat;

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
        Espresso.onView(ViewMatchers.withId(R.id.SigninEmail))
                .perform(ViewActions.typeText("druzhini@usc.edu"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.SigninPass))
                .perform(ViewActions.typeText("testing"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
                .perform(ViewActions.click());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.nav_chats)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.fragment_chats))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSearchChatPage() {
        // Perform login
        Espresso.onView(ViewMatchers.withId(R.id.SigninEmail))
                .perform(ViewActions.typeText("druzhini@usc.edu"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.SigninPass))
                .perform(ViewActions.typeText("testing"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
                .perform(ViewActions.click());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.nav_chats)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.chat_search_btn))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.activity_contact_search))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSearchUserForChat() {
        // Perform login
        Espresso.onView(ViewMatchers.withId(R.id.SigninEmail))
                .perform(ViewActions.typeText("druzhini@usc.edu"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.SigninPass))
                .perform(ViewActions.typeText("testing"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
                .perform(ViewActions.click());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.nav_chats)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.chat_search_btn))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.seach_username_input))
                .perform(ViewActions.typeText("sidqian"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.search_user_btn))
                .perform(ViewActions.click());
        //check the first recycler view item
        Espresso.onView(ViewMatchers.withId(R.id.search_user_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        Espresso.onView(ViewMatchers.withId(R.id.activity_inside_chat))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void TestSendMessageAndBackHome() {
        // Perform login
        Espresso.onView(ViewMatchers.withId(R.id.SigninEmail))
                .perform(ViewActions.typeText("druzhini@usc.edu"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.SigninPass))
                .perform(ViewActions.typeText("testing"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
                .perform(ViewActions.click());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.nav_chats)).perform(ViewActions.click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.chat_search_btn))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.seach_username_input))
                .perform(ViewActions.typeText("sidqian"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.search_user_btn))
                .perform(ViewActions.click());
        //check the first recycler view item
        Espresso.onView(ViewMatchers.withId(R.id.search_user_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        Espresso.onView(ViewMatchers.withId(R.id.chat_message_input))
                .perform(ViewActions.typeText("test"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.message_send_btn));
        //back to home
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.onView(ViewMatchers.withId(R.id.activity_main))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }




    @After
    public void tearDown() {
        Intents.release();
    }
}
