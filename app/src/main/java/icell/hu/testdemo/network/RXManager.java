package icell.hu.testdemo.network;

import icell.hu.testdemo.network.Interfaces.LoginListener;
import icell.hu.testdemo.network.Interfaces.VehicleListener;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;
import rx.Subscription;

/**
 * Created by ilaszlo on 27/09/16.
 */

public interface RXManager {

    Subscription login (DemoCredentials userCredentials , LoginListener loginListener ) ;

    Subscription getAvailableVehicles ( SelectedUser user , VehicleListener loginListener );
}