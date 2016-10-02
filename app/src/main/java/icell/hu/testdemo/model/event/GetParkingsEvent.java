package icell.hu.testdemo.model.event;

import java.util.List;

import icell.hu.testdemo.model.Parking;

/**
 * Created by User on 2016. 09. 30..
 */

public class GetParkingsEvent extends EventParent{

    List<Parking> parking;

    public GetParkingsEvent(){}

    public GetParkingsEvent(List<Parking> parking) {
        this.parking = parking;
    }

    public List<Parking> getParking() {
        return parking;
    }
}
