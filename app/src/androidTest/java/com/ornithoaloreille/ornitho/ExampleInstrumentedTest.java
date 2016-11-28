package com.ornithoaloreille.ornitho;

import android.app.Fragment;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ornithoaloreille.ornitho.view.GroupFragment;
import com.ornithoaloreille.ornitho.view.GroupListFragment;
import com.ornithoaloreille.ornitho.view.MainActivity;
import com.ornithoaloreille.ornitho.view.QuizListFragment;
import com.ornithoaloreille.ornitho.view.SpeciesFragment;
import com.ornithoaloreille.ornitho.view.SpeciesListFragment;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withTagKey;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private DrawerLayout drawerLayout;
    private RecyclerView drawer;
    private ActionBarDrawerToggle drawerToggle;

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        drawerLayout = (DrawerLayout) testRule.getActivity().findViewById(R.id.drawer_layout);
        drawer = (RecyclerView) testRule.getActivity().findViewById(R.id.drawer);
        drawerToggle = testRule.getActivity().getDrawerToggle();
    }

    @Test
    public void initially() {
        // Fragment is Group List
        Fragment fragment = testRule.getActivity().getFragmentManager().findFragmentById(R.id.fragment_container);
        Assert.assertTrue(fragment instanceof GroupListFragment);

        // Drawer is closed
        Assert.assertFalse(drawerLayout.isDrawerOpen(drawer));
    }

    @Test
    public void goToGroups() {
        onView(withId(16908347)).perform(click());
        onView(allOf(withId(R.id.drawer_name), withText(R.string.groups))).perform(click());
        Fragment fragment = testRule.getActivity().getFragmentManager().findFragmentById(R.id.fragment_container);
        Assert.assertTrue(fragment instanceof GroupListFragment);
    }

    @Test
    public void goToSpecies() {
        onView(withId(16908347)).perform(click());
        onView(allOf(withId(R.id.drawer_name), withText(R.string.species))).perform(click());
        Fragment fragment = testRule.getActivity().getFragmentManager().findFragmentById(R.id.fragment_container);
        Assert.assertTrue(fragment instanceof SpeciesListFragment);
    }

    @Test
    public void goToQuiz() {
        onView(withId(16908347)).perform(click());
        onView(allOf(withId(R.id.drawer_name), withText(R.string.quizz))).perform(click());
        Fragment fragment = testRule.getActivity().getFragmentManager().findFragmentById(R.id.fragment_container);
        Assert.assertTrue(fragment instanceof QuizListFragment);
    }

    @Test
    public void fromGroupsToGroup() {
        onView(withText("Rapaces")).perform(click());
        Fragment fragment = testRule.getActivity().getFragmentManager().findFragmentById(R.id.fragment_container);
        Assert.assertTrue(fragment instanceof GroupFragment);
    }

    @Test
    public void fromGroupToSpecies() {
        onView(withText("Rapaces")).perform(click());
        onView(withText("Petite buse")).perform(click());
        Fragment fragment = testRule.getActivity().getFragmentManager().findFragmentById(R.id.fragment_container);
        Assert.assertTrue(fragment instanceof SpeciesFragment);
    }

    @Test
    public void fromSpeciesToSpecie() {
        onView(withId(16908347)).perform(click());
        onView(allOf(withId(R.id.drawer_name), withText(R.string.species))).perform(click());
        onView(withText("Alouette hausse-col")).perform(click());
        Fragment fragment = testRule.getActivity().getFragmentManager().findFragmentById(R.id.fragment_container);
        Assert.assertTrue(fragment instanceof SpeciesFragment);
    }

    @Test
    public void fromSpecieToSpecie() {
        onView(withId(16908347)).perform(click());
        onView(allOf(withId(R.id.drawer_name), withText(R.string.species))).perform(click());
        onView(withText("Alouette hausse-col")).perform(click());
        onView(withText("Pipit d'Amérique")).perform(click());
        try {
            onView(withText("Alouette hausse-col")).perform(click());
        } catch(Exception e) {
            Assert.assertTrue(false);
        }
    }
}
