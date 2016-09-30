package icell.hu.testdemo.network.Interfaces;

import java.util.List;

import icell.hu.testdemo.model.UserInfo;
import icell.hu.testdemo.model.Vehicle;

/**
 * Created by User on 2016. 09. 27..
 */

public interface VehicleListener extends ErrorListener {

    void vehiclesLoaded(List<Vehicle> vehicles ) ;

    void vehiclesAdded( Vehicle vehicles ) ;

}
