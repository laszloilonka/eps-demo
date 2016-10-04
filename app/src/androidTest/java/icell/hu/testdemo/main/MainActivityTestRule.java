package icell.hu.testdemo.main;

import android.support.test.rule.ActivityTestRule;

import icell.hu.testdemo.MainActivity;
import icell.hu.testdemo.ui.fragment.VehiclesFragment;

/**
 * Created by User on 2016. 10. 04..
 */

public class MainActivityTestRule extends ActivityTestRule<MainActivity> {

    public VehiclesFragment vehiclesFragment;
    MockMainEventBusManager eventBusManager;


    public MainActivityTestRule() {
        super(MainActivity.class);
    }
    public MainActivityTestRule(MockMainEventBusManager eventBusManager) {
        super(MainActivity.class);
        setEventBusManager(eventBusManager);
    }


    public void setEventBusManager(MockMainEventBusManager eventBusManager) {
        this.eventBusManager = eventBusManager;
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();
        System.out.println("Set fragment");

        vehiclesFragment =
                (VehiclesFragment) getActivity().getSupportFragmentManager()
                        .findFragmentById(android.R.id.content);
        eventBusManager.setVehiclesFragment(vehiclesFragment);
        eventBusManager.setParkingsDownloadedEvent(null);
    }
}
