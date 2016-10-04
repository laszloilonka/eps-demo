package icell.hu.testdemo.main;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import icell.hu.testdemo.DemoApplication;
import icell.hu.testdemo.MainActivity;
import icell.hu.testdemo.R;
import icell.hu.testdemo.di.DemoModule;
import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.model.event.ParkingsDownloadedEvent;
import icell.hu.testdemo.model.event.VehiclesDownloadedEvent;
import icell.hu.testdemo.network.DemoClient;
import icell.hu.testdemo.network.EventBusManager;
import icell.hu.testdemo.ui.fragment.VehiclesFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule;

    EventBusManager mockEventBusManager = new MockMainEventBusManager();

    @Before
    public void setUp() {
        //mainActivityTestRule = new MainActivityTestRule();

        DemoApplication application = (DemoApplication) InstrumentationRegistry
                .getInstrumentation().getTargetContext().getApplicationContext();
        application.reset();
        application.setDemoModule(new TestModule());

    }

    @Test
    public void testStartApp() throws Exception {
        // when
        setVehicles();
        setParkings();
        mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);
        MainActivity activity = mainActivityTestRule.launchActivity(null);
        VehiclesFragment vehiclesFragment =
                (VehiclesFragment) activity.getSupportFragmentManager()
                        .findFragmentById(android.R.id.content);
        ((MockMainEventBusManager) mockEventBusManager).setVehiclesFragment(vehiclesFragment);


        // given
        onView(ViewMatchers.withId(R.id.spinner)).perform(click());


        // then
        //verify(mainActivityTestRule.vehiclesFragment.spinner.getAdapter().getCount()).equals(1);
    }


    private void setVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        Vehicle vehicle = new Vehicle();
        vehicle.setPlateNumber("test");
        vehicle.setVehicleId(1L);
        vehicles.add(vehicle);
        VehiclesDownloadedEvent event = new VehiclesDownloadedEvent(vehicles);
        ((MockMainEventBusManager) mockEventBusManager).setVehiclesDownloadedEvent(event);
    }

    private void setParkings() {
        List<Parking> parkingList = new ArrayList<>();
        Parking parking = new Parking();
        parking.setStartedAt(12312312L);
        parking.setParkingId(2L);
        parking.setVehicleId(1L);
        parkingList.add(parking);
        ParkingsDownloadedEvent event = new ParkingsDownloadedEvent(parkingList);
        ((MockMainEventBusManager) mockEventBusManager).setParkingsDownloadedEvent(event);
    }

    @Module
    public class TestModule extends DemoModule {

        @Override
        public EventBusManager provideEventBusManager(DemoClient demoClient) {
            return mockEventBusManager;
        }
    }
}
