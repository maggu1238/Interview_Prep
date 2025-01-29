package controllers;

import models.City;
import models.Vehicle;

public interface IVehicleManager {
    Vehicle registerVehicle(String id, City city, String type, String vehicleType);
}
