package icell.hu.testdemo.model;

/**
 * Created by ilaszlo on 13/09/16.
 */

public class Vehicle {

    private Long userId;
    private Long vehicleId;
    private String plateNumber;


    public Vehicle() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }


    @Override
    public boolean equals(Object obj) {
        return ((Vehicle)obj).getVehicleId() == vehicleId &&
                ((Vehicle)obj).getUserId() == userId;
    }
}
