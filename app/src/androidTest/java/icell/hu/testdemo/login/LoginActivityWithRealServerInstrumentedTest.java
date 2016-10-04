package icell.hu.testdemo.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dagger.Module;
import icell.hu.testdemo.DemoApplication;
import icell.hu.testdemo.LoginActivity;
import icell.hu.testdemo.MainActivity;
import icell.hu.testdemo.R;
import icell.hu.testdemo.di.DemoModule;
import icell.hu.testdemo.singleton.ActivityPresenter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityWithRealServerInstrumentedTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityTestRule;

    ActivityPresenter mockActivityPresenter;

    @Before
    public void setUp() {
        mockActivityPresenter = mock(ActivityPresenter.class);
        DemoApplication application = (DemoApplication) InstrumentationRegistry
                .getInstrumentation().getTargetContext().getApplicationContext();
        application.reset();
        application.setDemoModule(new TestModule());
    }

    @Test(timeout = 5000)
    public void testUserAuthenticationSucceeded() throws Exception {
        // given
        LoginActivity activity = initLoginActivity();

        // when
        onView(ViewMatchers.withId(R.id.email)).perform(typeText("icell"));
        onView(withId(R.id.password)).perform(typeText("icell"));
        onView(withId(R.id.custum_button)).perform(click());

        // then
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        expectedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        verify(mockActivityPresenter).startActivity(any(LoginActivity.class),
                argThat(new IntentMatcher(expectedIntent)));
    }

    @Test(timeout = 5000)
    public void testUserAuthenticationFailed() throws Exception {
        // given
        LoginActivity activity = initLoginActivity();

        // when
        onView(withId(R.id.email)).perform(typeText(""));
        onView(withId(R.id.password)).perform(typeText(""));
        onView(withId(R.id.custum_button)).perform(click());

        // then
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        expectedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        verify(mockActivityPresenter, times(0)).startActivity(any(LoginActivity.class),
                argThat(new IntentMatcher(expectedIntent)));
    }

    @Test(timeout = 5000)
    public void testUserAuthenticationError() throws Exception {
        // given
        LoginActivity activity = initLoginActivity();

        // when
        onView(withId(R.id.email)).perform(typeText("sdsa"));
        onView(withId(R.id.password)).perform(typeText("hibasPass"));
        onView(withId(R.id.custum_button)).perform(click());

        // then
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        expectedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        verify(mockActivityPresenter, times(0)).startActivity(any(LoginActivity.class),
                argThat(new IntentMatcher(expectedIntent)));
    }

    @NonNull
    private LoginActivity initLoginActivity() {
        loginActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
        LoginActivity activity = loginActivityTestRule.launchActivity(null);

        /*LoginFragment loginFragment = (LoginFragment) activity.getSupportFragmentManager()
                .findFragmentById(android.R.id.content);*/

        return activity;
    }


    @Module
    public class TestModule extends DemoModule {
        @Override
        public ActivityPresenter provideActivityPresenter() {
            return mockActivityPresenter;
        }

    }
}
