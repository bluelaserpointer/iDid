package com.example.myapplicationtest1.page;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplicationtest1.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class GalleryPageTest {

    @Rule
    public ActivityTestRule<GalleryPage> rule = new ActivityTestRule<>(GalleryPage.class);

    @Test
    public void uiCreateTest() {
        onView(withId(R.id.toTeam_Button)).check(matches(isDisplayed()));
        onView(withId(R.id.toItemStorage_Button)).check(matches(isDisplayed()));
        onView(withId(R.id.toCardStorage_Button)).check(matches(isDisplayed()));
        onView(withId(R.id.return_button)).check(matches(isDisplayed()));
    }
}