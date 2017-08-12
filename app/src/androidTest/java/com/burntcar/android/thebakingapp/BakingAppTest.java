package com.burntcar.android.thebakingapp;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.burntcar.android.thebakingapp.R.id.recipe_desc_tv;
import static org.hamcrest.Matchers.anything;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;


/**
 * Created by Harshraj on 12-08-2017.
 */

@RunWith(AndroidJUnit4.class)
public class BakingAppTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void EntireAppUiFlowTest() {

        //Note: These test cases are for mobile screen only!

        onView(withId(R.id.recyclerview_recipe_name)).check(matches(hasDescendant(withText("Nutella Pie"))));

        onView(nthChildOf(withId(R.id.recyclerview_recipe_name), 0)).check(matches(hasDescendant(withText("Nutella Pie"))));
        onView(nthChildOf(withId(R.id.recyclerview_recipe_name), 1)).check(matches(hasDescendant(withText("Brownies"))));
        onView(nthChildOf(withId(R.id.recyclerview_recipe_name), 2)).check(matches(hasDescendant(withText("Yellow Cake"))));
        onView(nthChildOf(withId(R.id.recyclerview_recipe_name), 3)).check(matches(hasDescendant(withText("Cheesecake"))));


        onView(withId(R.id.recyclerview_recipe_name))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView((withId(R.id.recipe_steps_tv1))).check(matches(withText("Ingredients")));
        onView(nthChildOf(withId(R.id.recipe_list), 0)).check(matches(hasDescendant(withText("Step 1 : Recipe Introduction"))));

        onView(withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        onView((withId(R.id.recipe_detail))).check(matches(withText("Recipe Introduction")));

        onView((withId(R.id.next_step_btn)))
                .perform(click());

        onView((withId(R.id.recipe_detail))).check(matches(withText("Starting prep")));
    }


    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with " + childPosition + " child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(childPosition).equals(view);
                //return parentMatcher.matches(view.getParent()) && view.equals(group.getChildAt(childPosition));
            }
        };
    }


}
