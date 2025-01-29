package services;

import enums.VehicleState;
import models.Vehicle;

import java.util.List;

public interface IVehicleService {
    boolean registerVehicle(Vehicle vehicle);
    VehicleState getVehicleState(String vehicleId);
    void updateVehicleLocation(String vehicleId, String newCityId);
    void updateVehicleState(String vehicleId, VehicleState newState);
    Vehicle getVehicleToBook(List<String> availableCabIds);
}
