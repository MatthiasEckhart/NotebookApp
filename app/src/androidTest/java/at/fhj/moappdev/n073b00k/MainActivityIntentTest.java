package at.fhj.moappdev.n073b00k;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import at.fhj.moappdev.n073b00k.activities.AddActivity;
import at.fhj.moappdev.n073b00k.activities.MainActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;


@RunWith(AndroidJUnit4.class)
public class MainActivityIntentTest {

    @Rule
    public IntentsTestRule<MainActivity> mIntentRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        closeSoftKeyboard();
    }

    @Test
    public void startAddActivityTest() {
        onView(withId(R.id.main_add)).perform(click());
        intended(hasComponent(AddActivity.class.getName()));
    }
}