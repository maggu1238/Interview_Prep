package factories;

import models.*;

public class VehicleFactory {
    public static Vehicle createVehicle(String id, City currentCity, String type, String vehicleType) {
        if (vehicleType.equalsIgnoreCase("Cab")) {
            return new Cab(id, currentCity, type); // Sedan, SUV, etc.
        }
        else {
            throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        }
    }
}
