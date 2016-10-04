package icell.hu.testdemo;

import com.google.gson.Gson;

import org.junit.Test;

import icell.hu.testdemo.model.Vehicle;

import static org.junit.Assert.assertEquals;

/**
 * Created by User on 2016. 09. 30..
 */

public class VehicleTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        Gson gson = new Gson();

        Vehicle vehicle = new Vehicle();
        vehicle.setPlateNumber("AAA-111");

        System.out.println(gson.toJson(vehicle));

    }


}
