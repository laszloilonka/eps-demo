package icell.hu.testdemo.singleton;

import java.util.List;

import icell.hu.testdemo.model.Parking;

/**
 * Created by User on 2016. 09. 29..
 */

public class ParkingsImpl implements Parkings {

    List<Parking> parkings;

    @Override
    public List<Parking> parking() {
        return parkings;
    }

    @Override
    public void setVehicles(List<Parking> parkings) {
        this.parkings = parkings;
    }
}
