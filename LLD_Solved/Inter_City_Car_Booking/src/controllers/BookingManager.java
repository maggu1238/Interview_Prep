package controllers;

import models.*;

import java.util.*;

public class BookingManager implements IBookingManager {

    @Override
    public Vehicle bookVehicle(City city) {
        List<Vehicle> availableVehicles = findAvailableVehicles(city);
        if (availableVehicles.isEmpty()) {
            System.out.println("No vehicles available in " + city.getName());
            return null;
        }
        availableVehicles.sort(Comparator.comparingLong(Vehicle::getLastIdleTimestamp));
        return availableVehicles.get(0)

    private List<Vehicle> findAvailableVehicles(City city) {
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle vehicle : city.getVehicles()) {
            if (vehicle.getState() == VehicleState.IDLE) {
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }
}
