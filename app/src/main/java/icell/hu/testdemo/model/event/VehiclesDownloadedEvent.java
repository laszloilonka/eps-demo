package icell.hu.testdemo.model.event;

import java.util.List;

import icell.hu.testdemo.model.Vehicle;

/**
 * Created by User on 2016. 09. 30..
 */

public class VehiclesDownloadedEvent extends EventParent{

    List<Vehicle> vehicles;

    public VehiclesDownloadedEvent(){}

    public VehiclesDownloadedEvent(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
