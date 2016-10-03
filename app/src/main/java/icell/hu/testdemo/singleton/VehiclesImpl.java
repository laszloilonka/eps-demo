package icell.hu.testdemo.singleton;

import android.util.Log;
import android.util.LongSparseArray;

import java.util.ArrayList;
import java.util.List;

import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.Vehicle;

/**
 * Created by User on 2016. 09. 29..
 */

public class VehiclesImpl implements Vehicles {

    public static final String TAG = VehiclesImpl.class.getSimpleName();
    LongSparseArray<Vehicle> vehicles = new LongSparseArray<>();

    LongSparseArray<Parking> parkings = new LongSparseArray<>();

    @Override
    public LongSparseArray<Parking> getVehicleParkings() {
        return parkings;
    }

    @Override
    public LongSparseArray<Vehicle> getVehicles() {
        return vehicles;
    }


    @Override
    public void addVehicle(Vehicle vehicle) {
        getVehicles().put(vehicle.getVehicleId(), vehicle);
    }

    @Override
    public void addParking(Parking parking) {
        parkings.put(parking.getParkingId(), parking);
        Log.d(TAG, "parking size:" + parkings.size() + " - id:" + parking.getParkingId());
    }

    @Override
    public Parking getLatestParking(Long vehicleID) {
        Log.d(TAG, "vehicleID:" + vehicleID);
        Parking parking = null;
        for (int i = parkings.size()-1; 0 < i ; i--) {
            if (vehicleID == parkings.get(parkings.keyAt(i)).getVehicleId()) {
                parking = parkings.get(parkings.keyAt(i));                                          // TODO
                return parking;
            }
        }
        return parking;
    }

    @Override
    public List<Parking> getVehicleParkings(Long vehicleID) {
        List<Parking> parkingList = new ArrayList<>();
        for (int i = parkings.size()-1; 0 < i ; i--) {
            if (vehicleID == parkings.get(parkings.keyAt(i)).getVehicleId()) {
                parkingList.add( parkings.get(parkings.keyAt(i)));
            }
        }
        return parkingList;
    }
}