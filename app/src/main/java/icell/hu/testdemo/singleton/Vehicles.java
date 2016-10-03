package icell.hu.testdemo.singleton;

import android.util.LongSparseArray;

import java.util.List;

import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.Vehicle;

/**
 * Created by User on 2016. 09. 29..
 */

public interface Vehicles {


    LongSparseArray<Vehicle> getVehicles();

    LongSparseArray<Parking> getVehicleParkings();

    void addVehicle(Vehicle vehicle);

    void addParking(Parking parking);

    Parking getLatestParking(Long vehicleID);

    List<Parking> getVehicleParkings(Long vehicleID);


}
