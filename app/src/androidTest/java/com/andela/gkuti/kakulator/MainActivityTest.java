package com.andela.gkuti.kakulator;

import android.test.ActivityInstrumentationTestCase2;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testOnCreate() throws Exception {
        getActivity();
        onView(withText("Kakulator")).check(matches(isDisplayed()));
    }

    public void testButtonOperations() throws Exception {
        getActivity();
        onView(withId(R.id.num1)).perform(click());
        onView(withId(R.id.num2)).perform(click());
        onView(withId(R.id.num3)).perform(click());
        onView(withId(R.id.num4)).perform(click());
        onView(withId(R.id.num0)).perform(click());
        onView(withId(R.id.num5)).perform(click());
        onView(withId(R.id.num6)).perform(click());
        onView(withId(R.id.num7)).perform(click());
        onView(withId(R.id.operation_point)).perform(click());
        onView(withId(R.id.num8)).perform(click());
        onView(withId(R.id.num9)).perform(click());
        onView(withText("USD12340567.89")).check(matches(isDisplayed()));
    }

    public void testActionBarIconTopTen() throws Exception {
        getActivity();
        onView(withId(R.id.action_top_ten)).perform(click());
        onView(withText("Top Ten")).check(matches(isDisplayed()));
    }

    public void testActionBarIconSettings() throws Exception {
        getActivity();
        onView(withId(R.id.action_settings)).perform(click());
        onView(withText("Settings")).check(matches(isDisplayed()));
    }

    public void testActionBarIconRefresh() throws Exception {
        getActivity();
        onView(withId(R.id.action_renew)).perform(click());
    }

    public void testAddButton() throws Exception {
        getActivity();
        onView(withId(R.id.num5)).perform(click());
        onView(withId(R.id.operation_add)).perform(click());
        onView(withId(R.id.num5)).perform(click());
        onView(withText("USD5 + USD5")).check(matches(isDisplayed()));
    }

    public void testSubtractButton() throws Exception {
        getActivity();
        onView(withId(R.id.num7)).perform(click());
        onView(withId(R.id.num3)).perform(click());
        onView(withId(R.id.operation_minus)).perform(click());
        onView(withId(R.id.num9)).perform(click());
        onView(withText("USD73 - USD9")).check(matches(isDisplayed()));
    }

    public void testDivideButton() throws Exception {
        getActivity();
        onView(withId(R.id.num8)).perform(click());
        onView(withId(R.id.num2)).perform(click());
        onView(withId(R.id.change_mode)).perform(click());
        onView(withId(R.id.operation_divide)).perform(click());
        onView(withId(R.id.num4)).perform(click());
        onView(withText("USD82 / 4")).check(matches(isDisplayed()));
    }

    public void testTimesButton() throws Exception {
        getActivity();
        onView(withId(R.id.num2)).perform(click());
        onView(withId(R.id.change_mode)).perform(click());
        onView(withId(R.id.operation_times)).perform(click());
        onView(withId(R.id.num6)).perform(click());
        onView(withText("USD2 * 6")).check(matches(isDisplayed()));
    }

    public void testCurrencySpinner() throws Exception {
        getActivity();
        onView(withId(R.id.spinner)).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText(containsString("UNITED STATES"))));
    }

    public void testEqualsButtonOnArithmeticMode() throws Exception {
        getActivity();
        onView(withId(R.id.change_mode)).perform(click());
        onView(withId(R.id.num2)).perform(click());
        onView(withId(R.id.operation_times)).perform(click());
        onView(withId(R.id.num6)).perform(click());
        onView(withText("2 * 6")).check(matches(isDisplayed()));
        onView(withId(R.id.operation_equals)).perform(click());
        onView(withId(R.id.tv_result)).check(matches(withText("12.00")));
    }

    public void testEqualsButtonOnCurrencyMode() throws Exception {
        getActivity();
        onView(withId(R.id.action_settings)).perform(click());
        onView(withText("Settings")).check(matches(isDisplayed()));
        onView(withId(R.id.settings_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("UNITED STATES"))).perform(click());
        onView(withId(R.id.settings_spinner)).check(matches(withSpinnerText(containsString("UNITED STATES"))));
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.num5)).perform(click());
        onView(withId(R.id.num0)).perform(click());
        onView(withId(R.id.num0)).perform(click());
        onView(withId(R.id.operation_minus)).perform(click());
        onView(withId(R.id.num1)).perform(click());
        onView(withId(R.id.num8)).perform(click());
        onView(withId(R.id.num0)).perform(click());
        onView(withText("USD500 - USD180")).check(matches(isDisplayed()));
        onView(withId(R.id.operation_equals)).perform(click());
        onView(withId(R.id.tv_result)).check(matches(withText("320.00")));
    }
    public void testClearButton() throws Exception {
        getActivity();
        onView(withId(R.id.action_settings)).perform(click());
        onView(withText("Settings")).check(matches(isDisplayed()));
        onView(withId(R.id.settings_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("UNITED STATES"))).perform(click());
        onView(withId(R.id.settings_spinner)).check(matches(withSpinnerText(containsString("UNITED STATES"))));
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.num7)).perform(click());
        onView(withId(R.id.num3)).perform(click());
        onView(withId(R.id.operation_minus)).perform(click());
        onView(withId(R.id.num9)).perform(click());
        onView(withText("USD73 - USD9")).check(matches(isDisplayed()));
        onView(withId(R.id.operation_equals)).perform(click());
        onView(withId(R.id.tv_result)).check(matches(withText("64.00")));
    }


}