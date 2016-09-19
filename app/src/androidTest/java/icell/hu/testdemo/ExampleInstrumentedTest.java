package icell.hu.testdemo;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.greenrobot.eventbus.EventBus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import icell.hu.testdemo.di.DemoModule;
import icell.hu.testdemo.model.UserInfo;
import icell.hu.testdemo.network.DemoApi;
import icell.hu.testdemo.network.DemoClient;
import icell.hu.testdemo.singleton.ActivityPresenter;
import icell.hu.testdemo.singleton.DemoCredentials;
import retrofit2.Call;
import retrofit2.Response;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityTestRule;

    DemoClient mockDemoClient;
    DemoApi mockDemoApi;
    ActivityPresenter mockActivityPresenter;

    @Before
    public void setUp() {
        mockDemoApi = mock(DemoApi.class);
        mockDemoClient = mock(DemoClient.class);
        mockActivityPresenter = mock(ActivityPresenter.class);

        DemoApplication application = (DemoApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        application.setDemoModule(new TestModule());
    }

    @Test
    public void useAppContext() throws Exception {
        loginActivityTestRule = new ActivityTestRule<>(
                LoginActivity.class);
        final LoginActivity activity = loginActivityTestRule.launchActivity(null);

        when(mockDemoClient.getDemoApi()).thenReturn(mockDemoApi);
        Call<UserInfo> mockCall = mock(Call.class);
        DemoCredentials demoCredentials = new DemoCredentials();
        demoCredentials.setUsername("ilaszlo");
        demoCredentials.setPassword("ilaszlo1");
       /* when(mockDemoApi.user(demoCredentials.getBasicScheme())).thenReturn(mockCall);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Response<UserInfo> mockResponse = Response.success(null);
                activity.onResponse(null, mockResponse);
                return null;
            }
        }).when(mockCall).enqueue(activity);

        onView(withId(R.id.email)).perform(typeText("ilaszlo"));
        onView(withId(R.id.password)).perform(typeText("ilaszlo1"));
        onView(withId(R.id.email_sign_in_button)).perform(click());

        verify(mockDemoApi).user(demoCredentials.getBasicScheme());
        verify(mockDemoClient).getDemoApi();
        Intent expectedIntent = new Intent(activity, MainActivity.class);
        expectedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        verify(mockActivityPresenter).startActivity(any(LoginActivity.class), argThat(new IntentMatcher(expectedIntent)));*/
    }

    @Module
    public class TestModule extends DemoModule {

        @Provides
        @Singleton
        @Override
        public DemoClient provideDemoClient(EventBus eventBus) {
            return mockDemoClient;
        }

        @Override
        public ActivityPresenter provideActivityPresenter() {
            return mockActivityPresenter;
        }
    }
}
