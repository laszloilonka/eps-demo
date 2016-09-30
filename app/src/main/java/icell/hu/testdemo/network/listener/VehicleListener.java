package icell.hu.testdemo.network.listener;

import icell.hu.testdemo.model.Vehicle;

/**
 * Created by User on 2016. 09. 27..
 */

public interface VehicleListener extends ErrorListener {

    void vehiclesFinished ( Vehicle[] vehicles );


}
