package icell.hu.testdemo.network.Interfaces;

import java.util.List;

import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.Vehicle;

/**
 * Created by User on 2016. 09. 29..
 */

public interface ParkingListener extends ErrorListener {

    void parkingLoaded (List<Parking> parkings );

    void onProcessFinished ( Parking parking) ;

}
