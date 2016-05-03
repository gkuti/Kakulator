package com.andela.gkuti.kakulator;
import android.test.ActivityInstrumentationTestCase2;

import com.andela.gkuti.kakulator.activity.SettingsActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class SettingsActivityTest extends ActivityInstrumentationTestCase2<SettingsActivity> {
    public SettingsActivityTest() {
        super(SettingsActivity.class);
    }
    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {

    }

    public void testOnCreate() throws Exception {
        getActivity();
        onView(withText("Settings")).check(matches(isDisplayed()));
        onView(withText("Base Currency")).check(matches(isDisplayed()));
        onView(withText("Update")).check(matches(isDisplayed()));
    }
    public void testChangeBaseCurrency() throws Exception {
        getActivity();
        onView(withId(R.id.settings_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("LIBYA"))).perform(click());
        onView(withId(R.id.settings_spinner)).check(matches(withSpinnerText(containsString("LIBYA"))));
    }
    public void testChangeAutoUpdate() throws Exception {
        getActivity();
        onView(withId(R.id.switch1)).perform(click());
        onView(withId(R.id.switch1)).perform(click());
    }
}