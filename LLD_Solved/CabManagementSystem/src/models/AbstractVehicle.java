package models;

import enums.VehicleState;

// Abstract class for shared vehicle functionality
public abstract class AbstractVehicle implements Vehicle {
    private String vehicleId;
    private VehicleState currentState;
    private String cityId;

    public AbstractVehicle(String vehicleId, VehicleState state) {
        this.vehicleId = vehicleId;
        this.currentState = state;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public VehicleState getState() {
        return currentState;
    }

    public void setState(VehicleState state) {
        this.currentState = state;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

}
