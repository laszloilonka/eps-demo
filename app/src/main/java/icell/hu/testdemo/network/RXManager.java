package icell.hu.testdemo.network;

import icell.hu.testdemo.model.AddVehicle;
import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.network.Interfaces.LoginListener;
import icell.hu.testdemo.network.Interfaces.ParkingListener;
import icell.hu.testdemo.network.Interfaces.VehicleListener;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;
import rx.Subscription;

/**
 * Created by ilaszlo on 27/09/16.
 */

public interface RXManager {

    Subscription login ( DemoCredentials userCredentials, LoginListener loginListener ) ;

    Subscription getVehicles ( SelectedUser selectedUser, VehicleListener loginListener );

    Subscription addVehicle ( SelectedUser selectedUser, AddVehicle vehicle , VehicleListener loginListener );

    Subscription getParkings ( SelectedUser selectedUser, ParkingListener loginListener );

    /*Subscription startParking ( SelectedUser user , ParkingListener loginListener );

    Subscription stopParking ( SelectedUser user , ParkingListener loginListener );*/


}