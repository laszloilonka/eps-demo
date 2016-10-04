package icell.hu.testdemo.main;

import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.model.event.ParkingsDownloadedEvent;
import icell.hu.testdemo.model.event.VehiclesDownloadedEvent;
import icell.hu.testdemo.network.EventBusManager;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;
import icell.hu.testdemo.ui.fragment.VehiclesFragment;

/**
 * Created by User on 2016. 10. 04..
 */

public class MockMainEventBusManager implements EventBusManager {

    VehiclesFragment vehiclesFragment;

    VehiclesDownloadedEvent vehiclesDownloadedEvent;
    ParkingsDownloadedEvent parkingsDownloadedEvent;


    public void setVehiclesFragment(VehiclesFragment vehiclesFragment) {
        this.vehiclesFragment = vehiclesFragment;
    }

    public void setVehiclesDownloadedEvent(VehiclesDownloadedEvent vehiclesDownloadedEvent) {
        this.vehiclesDownloadedEvent = vehiclesDownloadedEvent;
    }

    public void setParkingsDownloadedEvent(ParkingsDownloadedEvent parkingsDownloadedEvent) {
        this.parkingsDownloadedEvent = parkingsDownloadedEvent;
    }

    //  Events


    @Override
    public void login(DemoCredentials userCredentials) {

    }

    @Override
    public void getVehicles(SelectedUser selectedUser) {
        if (vehiclesFragment != null) {
            vehiclesFragment.onEvent(vehiclesDownloadedEvent);
        }
    }

    @Override
    public void addVehicle(SelectedUser selectedUser, Vehicle vehicle) {

    }

    @Override
    public void getParkings(SelectedUser selectedUser) {
        if (vehiclesFragment != null) {
            vehiclesFragment.onEvent(parkingsDownloadedEvent);
        }
    }

    @Override
    public void startParking(SelectedUser selectedUser, Vehicle vehicle) {

    }

    @Override
    public void stopParking(SelectedUser selectedUser, Parking parking) {

    }
}
