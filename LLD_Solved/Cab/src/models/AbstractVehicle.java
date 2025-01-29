package models;

import enums.VehicleState;

// Abstract class for shared vehicle functionality
public abstract class AbstractVehicle implements Vehicle {
    private String vehicleId;
    private VehicleState state;
    private String cityId;
    private long idleTime;

    public AbstractVehicle(String vehicleId, VehicleState state) {
        this.vehicleId = vehicleId;
        this.state = state;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public VehicleState getState() {
        return state;
    }

    public void setState(VehicleState state) {
        this.state = state;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public long getIdleTime() {
        return idleTime;
    }

    public void incrementIdleTime(long duration) {
        this.idleTime += duration;
    }
}
