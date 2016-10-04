package icell.hu.testdemo.model.event;

import icell.hu.testdemo.model.Parking;

/**
 * Created by User on 2016. 09. 30..
 */

public class ParkingStartedEvent extends EventParent{

    Parking parking;

    public ParkingStartedEvent(){}

    public ParkingStartedEvent(Parking vehicle) {
        this.parking = vehicle;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
