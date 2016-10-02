package icell.hu.testdemo.singleton;

import java.util.List;

import icell.hu.testdemo.model.Parking;


/**
 * Created by User on 2016. 09. 29..
 */

public interface Parkings {

    List<Parking> getParkings();

    void setParking(List<Parking> parkings);

}
