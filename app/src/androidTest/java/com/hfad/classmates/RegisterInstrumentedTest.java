package com.hfad.classmates;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.hfad.classmates.regLogInActivity.Register;

import org.junit.Rule;

@RunWith(AndroidJUnit4.class)
public class RegisterInstrumentedTest {

    @Rule
    public ActivityScenarioRule<Register> activityScenarioRule = new ActivityScenarioRule<>(Register.class);

    // firebase needs to not store each email address we use, as it returns that the registered email is already in use
    // test fails as we do not have a reusable email address to test this with
//    @Test
//    public void testRegistrationSuccess() {
//        Espresso.onView(ViewMatchers.withId(R.id.editTextTextEmailAddress))
//                .perform(ViewActions.typeText("valid@usc.edu"));
//
//        Espresso.onView(ViewMatchers.withId(R.id.RegisterPass))
//                .perform(ViewActions.typeText("password"));
//
//        Espresso.onView(ViewMatchers.withId(R.id.ConfirmPass))
//                .perform(ViewActions.typeText("password"), ViewActions.closeSoftKeyboard());
//
//        Espresso.onView(ViewMatchers.withId(R.id.RegisterButton))
//                .perform(ViewActions.click());
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Espresso.onView(ViewMatchers.withId(R.id.activity_main))
//                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
//    }

    @Test
    public void testNoEmail() {
        Espresso.onView(ViewMatchers.withId(R.id.RegisterButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextTextEmailAddress))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Please enter email")));
    }

    @Test
    public void testInvalidEmail() {
        Espresso.onView(ViewMatchers.withId(R.id.editTextTextEmailAddress))
                .perform(ViewActions.typeText("email@email.com"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.RegisterButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.editTextTextEmailAddress))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Please enter an USC email")));
    }

    @Test
    public void testNoPassword() {
        Espresso.onView(ViewMatchers.withId(R.id.editTextTextEmailAddress))
                .perform(ViewActions.typeText("testing@usc.edu"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.RegisterButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.RegisterPass))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Please enter password")));
    }

    @Test
    public void testInvalidPassword() {
        Espresso.onView(ViewMatchers.withId(R.id.editTextTextEmailAddress))
                .perform(ViewActions.typeText("test@usc.edu"));

        Espresso.onView(ViewMatchers.withId(R.id.RegisterPass))
                .perform(ViewActions.typeText("12345"));

        Espresso.onView(ViewMatchers.withId(R.id.ConfirmPass))
                .perform(ViewActions.typeText("12345"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.RegisterButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.RegisterPass))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Password should be at least 6 characters")));
    }

    @Test
    public void testNoConfirmationPassword() {
        Espresso.onView(ViewMatchers.withId(R.id.editTextTextEmailAddress))
                .perform(ViewActions.typeText("test@usc.edu"));

        Espresso.onView(ViewMatchers.withId(R.id.RegisterPass))
                .perform(ViewActions.typeText("123456"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.RegisterButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.ConfirmPass))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Please enter confirm password")));
    }

    @Test
    public void testInvalidConfirmationPassword() {
        Espresso.onView(ViewMatchers.withId(R.id.editTextTextEmailAddress))
                .perform(ViewActions.typeText("test@usc.edu"));

        Espresso.onView(ViewMatchers.withId(R.id.RegisterPass))
                .perform(ViewActions.typeText("123456"));

        Espresso.onView(ViewMatchers.withId(R.id.ConfirmPass))
                .perform(ViewActions.typeText("654321"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.RegisterButton))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.ConfirmPass))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Password and confirm password should be same")));
    }

    // firebase deals with this on its own, states the email address is already in use
    // maybe create another instance in the app where if the user inputs an email that already exists, let them know
    @Test
    public void testUsedEmail() {
        Espresso.onView(ViewMatchers.withId(R.id.editTextTextEmailAddress))
                .perform(ViewActions.typeText("druzhini@usc.edu"));

        Espresso.onView(ViewMatchers.withId(R.id.RegisterPass))
                .perform(ViewActions.typeText("password"));

        Espresso.onView(ViewMatchers.withId(R.id.ConfirmPass))
                .perform(ViewActions.typeText("password"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.RegisterButton))
                .perform(ViewActions.click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //if we failed to log in, the register button should still be visible
        Espresso.onView(ViewMatchers.withId(R.id.RegisterButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
