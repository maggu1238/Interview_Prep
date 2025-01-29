package controllers;

import factories.VehicleFactory;
import models.*;

public class VehicleManager implements IVehicleManager {
    @Override
    public Vehicle registerVehicle(String id, City city, String type, String vehicleType) {
        Vehicle vehicle = VehicleFactory.createVehicle(id, city, type, vehicleType);
        city.addVehicle(vehicle);
        return vehicle;
    }
}

