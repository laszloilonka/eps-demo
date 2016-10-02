package icell.hu.testdemo.model;

/**
 * Created by User on 2016. 09. 29..
 */

public class Parking {

    private Integer parkingId;
    private Integer userId;
    private Long vehicleId;
    private Long updatedAt;
    private Long startedAt;
    private Long finishedAt;

    public Parking() {

    }

    public Integer getParkingId() {
        return parkingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public Long getStartedAt() {
        return startedAt;
    }

    public Long getFinishedAt() {
        return finishedAt;
    }

    public boolean isParking(){
        return finishedAt == null;
    }

    public void setParkingId(Integer parkingId) {
        this.parkingId = parkingId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setStartedAt(Long startedAt) {
        this.startedAt = startedAt;
    }

    public void setFinishedAt(Long finishedAt) {
        this.finishedAt = finishedAt;
    }
}
