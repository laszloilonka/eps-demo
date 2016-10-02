package icell.hu.testdemo.model.event;

import icell.hu.testdemo.model.Parking;

/**
 * Created by User on 2016. 09. 30..
 */

public class ParkingStartEvent extends EventParent{

    Parking parking;

    public ParkingStartEvent(){}

    public ParkingStartEvent(Parking vehicle) {
        this.parking = vehicle;
    }

    public Parking getParking() {
        return parking;
    }
}
