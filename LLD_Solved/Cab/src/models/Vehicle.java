package models;

import enums.VehicleState;

public interface Vehicle {
    String getVehicleId();
    VehicleState getState();
    void setState(VehicleState state);
    String getCityId();
    void setCityId(String cityId);
    long getIdleTime();
    void incrementIdleTime(long duration);
}

