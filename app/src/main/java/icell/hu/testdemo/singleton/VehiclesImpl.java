package icell.hu.testdemo.singleton;

import java.util.ArrayList;
import java.util.List;

import icell.hu.testdemo.model.Vehicle;

/**
 * Created by User on 2016. 09. 29..
 */

public class VehiclesImpl implements Vehicles {

    List<Vehicle> vehicles ;

    @Override
    public List<Vehicle> veichles() {
        return vehicles;
    }

    @Override
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
