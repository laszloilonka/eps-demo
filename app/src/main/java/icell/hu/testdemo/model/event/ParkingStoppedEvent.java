package icell.hu.testdemo.model.event;

import icell.hu.testdemo.model.Parking;

/**
 * Created by User on 2016. 09. 30..
 */

public class ParkingStoppedEvent extends EventParent {

    Parking parking;

    public ParkingStoppedEvent(){}

    public ParkingStoppedEvent(Parking parking) {
        this.parking = parking;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
