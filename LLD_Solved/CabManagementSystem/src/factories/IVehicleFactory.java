package factories;

import enums.VehicleState;
import enums.VehicleType;
import models.Vehicle;

public interface IVehicleFactory {
    Vehicle createVehicle(String vehicleId, VehicleState state, VehicleType vehicleType);
}
