package dataLayer;

import enums.VehicleState;
import models.Vehicle;
import models.VehicleStateHistory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleRepository {
    private final Map<String, Vehicle> vehicles;

    private final Map<String, List<VehicleStateHistory>> vehicleHistory;

    private Logger logger = Logger.getLogger(VehicleRepository.class.getName());

    public VehicleRepository(){
        vehicles = new HashMap<>();
        vehicleHistory = new HashMap<>();
    }

    // Register a vehicle
    public void addVehicle(Vehicle vehicle) {
        if (vehicle == null || vehicle.getVehicleId() == null) {
            throw new IllegalArgumentException("Invalid vehicle data. Vehicle or Vehicle ID cannot be null.");
        }
        vehicles.put(vehicle.getVehicleId(), vehicle);
        vehicleHistory.putIfAbsent(vehicle.getVehicleId(), new ArrayList<>());
        addVehicleHistory(vehicle.getVehicleId(), vehicle.getState());
    }

    // Get a vehicle by ID
    public Vehicle getVehicle(String vehicleId) {
        if (vehicleId == null) {
            throw new IllegalArgumentException("Vehicle ID cannot be null.");
        }
        Vehicle vehicle = vehicles.get(vehicleId);
        if (vehicle == null) {
            logger.log(Level.WARNING, "No vehicle found with ID: {0}", vehicleId);
        }

        return vehicle;
    }

    // Update the history of a vehicle
    public void addVehicleHistory(String vehicleId, VehicleState newState) {

        if (vehicleId == null || newState == null) {
            throw new IllegalArgumentException("Vehicle ID or Vehicle State cannot be null.");
        }
        if (!vehicleHistory.containsKey(vehicleId)) {
            logger.log(Level.WARNING, "No history found for vehicle ID: {0}", vehicleId);
            return;
        }
        vehicleHistory.get(vehicleId).add(new VehicleStateHistory(newState, System.currentTimeMillis()));
    }

    // Get the history of a vehicle
    public List<VehicleStateHistory> getVehicleHistory(String vehicleId) {
        if (vehicleId == null) {
            throw new IllegalArgumentException("Vehicle ID cannot be null.");
        }

        List<VehicleStateHistory> history = vehicleHistory.get(vehicleId);
        if (history == null || history.isEmpty()) {
            logger.log(Level.WARNING, "No history found for vehicle ID: {0}", vehicleId);
            return new ArrayList<>();
        }

        return history;
    }
}