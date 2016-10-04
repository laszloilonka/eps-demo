package icell.hu.testdemo.login;

import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.model.event.LoginEvent;
import icell.hu.testdemo.network.EventBusManager;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;
import icell.hu.testdemo.ui.fragment.LoginFragment;

/**
 * Created by User on 2016. 10. 04..
 */

public class MockEventBusManager implements EventBusManager {

    LoginFragment loginFragment;

    LoginEvent loginEvent;

    public void setLoginFragment(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    public void setLoginEvent(LoginEvent loginEvent) {
        this.loginEvent = loginEvent;
    }

    @Override
    public void login(DemoCredentials userCredentials) {
        loginFragment.onEvent(loginEvent);
    }

    @Override
    public void getVehicles(SelectedUser selectedUser) {

    }

    @Override
    public void addVehicle(SelectedUser selectedUser, Vehicle vehicle) {

    }

    @Override
    public void getParkings(SelectedUser selectedUser) {

    }

    @Override
    public void startParking(SelectedUser selectedUser, Vehicle vehicle) {

    }

    @Override
    public void stopParking(SelectedUser selectedUser, Parking parking) {

    }
}
