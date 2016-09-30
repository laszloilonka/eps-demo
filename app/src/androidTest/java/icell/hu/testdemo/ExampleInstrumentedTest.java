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
import icell.hu.testdemo.di.DaggerDemoComponent;
import icell.hu.testdemo.di.DemoModule;
import icell.hu.testdemo.model.UserInfo;
import icell.hu.testdemo.network.DemoApi;
import icell.hu.testdemo.network.DemoClient;
import icell.hu.testdemo.network.RXManager;
import icell.hu.testdemo.network.RXManagerImpl;
import icell.hu.testdemo.singleton.ActivityPresenter;
import icell.hu.testdemo.singleton.DemoCredentials;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
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

    DemoClient mockDemoClient = mock(DemoClient.class);
    DemoApi mockDemoApi;
    ActivityPresenter mockActivityPresenter;
    private RXManagerImpl rxManager;

    @Before
    public void setUp() {
       // mockDemoApi = new MockDemoApi(); //mock(DemoApi.class);
       // mockDemoClient = mock(DemoClient.class);
        mockActivityPresenter = mock(ActivityPresenter.class);
      //  mockDemoApi = new MockDemoApi(Observable.just(new UserInfo()), null);

        DemoApplication application = (DemoApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        application.reset();
        application.setDemoModule(new TestModule());

    }

    @Test
    public void testUserAuthenticationSucceeded() throws Exception {
        loginActivityTestRule = new ActivityTestRule<>(
                LoginActivity.class);
        final LoginActivity activity = loginActivityTestRule.launchActivity(null);

       // rxManager.setDemoClient(mockDemoClient);
        mockDemoApi = new MockDemoApi(Observable.just(new UserInfo()), null);
        when(mockDemoClient.getDemoApi()).thenReturn(mockDemoApi);
        Call<UserInfo> mockCall = mock(Call.class);
        onView(withId(R.id.email)).perform(typeText("ilaszlo"));
        onView(withId(R.id.password)).perform(typeText("ilaszlo1"));
        onView(withId(R.id.email_sign_in_button)).perform(click());

        Intent expectedIntent = new Intent(activity, MainActivity.class);
        expectedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        verify(mockDemoClient).getDemoApi();
        //reset(mockDemoClient);
        verify(mockActivityPresenter).startActivity(any(LoginActivity.class), argThat(new IntentMatcher(expectedIntent)));

    }

    @Test
    public void testUserAuthenticationFailed() throws Exception {
        loginActivityTestRule = new ActivityTestRule<>(
                LoginActivity.class);
        final LoginActivity activity = loginActivityTestRule.launchActivity(null);

       // rxManager.setDemoClient(mockDemoClient);
        mockDemoApi = new MockDemoApi(Observable.error(new Exception("asd")), null);
        when(mockDemoClient.getDemoApi()).thenReturn(mockDemoApi);
        Call<UserInfo> mockCall = mock(Call.class);
        onView(withId(R.id.email)).perform(typeText("ilaszlo"));
        onView(withId(R.id.password)).perform(typeText("ilaszlo1"));
        onView(withId(R.id.email_sign_in_button)).perform(click());

        Intent expectedIntent = new Intent(activity, MainActivity.class);
        expectedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        verify(mockDemoClient).getDemoApi();
        //reset(mockDemoClient);
        verify(mockActivityPresenter).startActivity(any(LoginActivity.class), argThat(new IntentMatcher(expectedIntent)));

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

        @Override
        public RXManager provideRxManager(DemoClient demoClient) {
            rxManager = new RXManagerImpl(mockDemoClient);
            return rxManager;
        }
    }
}
