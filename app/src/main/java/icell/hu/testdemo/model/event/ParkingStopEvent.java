package icell.hu.testdemo.model.event;

import icell.hu.testdemo.model.Parking;

/**
 * Created by User on 2016. 09. 30..
 */

public class ParkingStopEvent extends EventParent {

    Parking parking;

    public ParkingStopEvent(){}

    public ParkingStopEvent(Parking parking) {
        this.parking = parking;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
