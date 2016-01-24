package at.fhj.moappdev.n073b00k;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import at.fhj.moappdev.n073b00k.activities.AddActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class AddActivityTest {

    @Rule
    public ActivityTestRule<AddActivity> mActivityRule = new ActivityTestRule<>(AddActivity.class);

    @Before
    public void setup() {
        closeSoftKeyboard();
    }

    @Test
    public void titleLabelTest() {
        onView(withId(R.id.add_titleLabel)).check(matches(withText(R.string.activity_add_title)));
    }
}