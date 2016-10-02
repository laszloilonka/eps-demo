package icell.hu.testdemo.model.event;

import icell.hu.testdemo.model.Vehicle;

/**
 * Created by User on 2016. 09. 30..
 * @see icell.hu.testdemo.ui.dialog.AddVehicleDialog
 */

public class AddedVehicleEvent extends EventParent{

    Vehicle vehicle;

    public AddedVehicleEvent(){

    }

    public AddedVehicleEvent(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
