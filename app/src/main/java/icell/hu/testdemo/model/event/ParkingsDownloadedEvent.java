package icell.hu.testdemo.model.event;

import java.util.List;

import icell.hu.testdemo.model.Parking;

/**
 * Created by User on 2016. 09. 30..
 */

public class ParkingsDownloadedEvent extends EventParent{

    List<Parking> parking;

    public ParkingsDownloadedEvent(){}

    public ParkingsDownloadedEvent(List<Parking> parking) {
        this.parking = parking;
    }

    public List<Parking> getParking() {
        return parking;
    }
}
