package com.andylai.aad;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.Espresso;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

	@Rule
	public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

//	@Test
//	public void mainActivityTest() {
//		ViewInteraction appCompatButton = onView(
//				allOf(withId(R.id.query_data), withText("Query Data"),
//						childAtPosition(
//								childAtPosition(
//										withId(android.R.id.content),
//										0),
//								0),
//						isDisplayed()));
//		appCompatButton.perform(click());
//	}

	private static Matcher<View> childAtPosition(
			final Matcher<View> parentMatcher, final int position) {

		return new TypeSafeMatcher<View>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("Child at position " + position + " in parent ");
				parentMatcher.describeTo(description);
			}

			@Override
			public boolean matchesSafely(View view) {
				ViewParent parent = view.getParent();
				return parent instanceof ViewGroup && parentMatcher.matches(parent)
						&& view.equals(((ViewGroup) parent).getChildAt(position));
			}
		};
	}
//
	@Test
	public void onClick() {
		Espresso.onView(withId(R.id.query_data)).perform(click());

		Espresso.onView(withId(R.id.edit_name)).perform(typeText("Andy"), closeSoftKeyboard());
		Espresso.onView(withId(R.id.edit_name)).check(matches(withText("Andy")));

		Espresso.onView(withId(R.id.edit_grade)).perform(typeText("133"), closeSoftKeyboard());
		Espresso.onView(withId(R.id.add)).perform(click());


		Espresso.onView(withId(R.id.edit_name)).perform(typeText("May"), closeSoftKeyboard());
		Espresso.onView(withId(R.id.edit_grade)).perform(typeText("169"), closeSoftKeyboard());
		Espresso.onView(withId(R.id.add)).perform(click());

		Espresso.onView(withId(R.id.edit_identifier)).perform(typeText("0"));
		Espresso.onView(withId(R.id.retrieve)).perform(click());

		Espresso.onIdle();

		Espresso.onView(withId(R.id.edit_identifier)).perform(typeText("1"));
		Espresso.onView(withId(R.id.retrieve)).perform(click());

		Espresso.onView(withId(R.id.edit_identifier)).perform(typeText("2"));
		Espresso.onView(withId(R.id.retrieve)).perform(click());
//		Espresso
//		Espresso.pressBack();
//		Espresso.onView(withId(R.id.recycler)).perform(click());
//		Espresso.pressBack();
//		Espresso.onView(withId(R.id.job_scheduler)).perform(click());
//		Espresso.pressBack();
//		Espresso.onView(withId(R.id.async_task)).perform(click());
//		Espresso.pressBack();
//		Espresso.onView(withId(R.id.drawer)).perform(click());
//		Espresso.pressBack();
//		Espresso.onView(withId(R.id.start_service)).perform(click());
//		Espresso.pressBack();
//		Espresso.onView(withId(R.id.bind_service)).perform(click());
//		Espresso.pressBack();
	}
}
