package icell.hu.testdemo;

import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by User on 2016. 09. 30..
 */

public class ParkingTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        Gson gson = new Gson();

        /*Vehicles vehicles = new Vehicles() {
            @Override
            public List<Vehicle> getVeichles() {
                List<Vehicle> vehicleList = new ArrayList<>();
                Vehicle vehicle = new Vehicle();
                vehicle.setPlateNumber("AAA-1111");

                List<Parking> parkingList = new ArrayList<>();
                Parking parking = new Parking();
                parking.setVehicleId(1212312L);
                parkingList.add(parking);
                //vehicle.setParkings(parkingList);

                vehicleList.add(vehicle);
                return vehicleList;
        }

            @Override
            public void setVehicles(List<Vehicle> veichles) {

            }
        };

        System.out.println(gson.toJson(vehicles.getVeichles()));*/

    }


}
