package icell.hu.testdemo.network;

import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;

/**
 * Created by User on 2016. 09. 30..
 */
public interface EventBusManager {

    void login(DemoCredentials userCredentials);

    void getVehicles(SelectedUser selectedUser);

    void addVehicle(SelectedUser selectedUser, Vehicle vehicle);

    void getParkings(SelectedUser selectedUser);

    void startParking(SelectedUser selectedUser, Vehicle vehicle);

    void stopParking(SelectedUser selectedUser, Parking parking);


}
