package models;

import enums.VehicleState;

public interface IVehicle {
    String getVehicleId();
    VehicleState getState();
    void setState(VehicleState state);
    String getCityId();
    void setCityId(String cityId);
    long getIdleTime();
    void addHistoryEntry(VehicleHistory history);
    List<VehicleHistory> getHistory();
}