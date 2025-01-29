package factories;

import enums.VehicleState;
import enums.VehicleType;
import models.Cab;
import models.Vehicle;

public class VehicleFactory implements  IVehicleFactory{
    @Override
    public Vehicle createVehicle(String vehicleId, VehicleState state, VehicleType vehicleType) {

        switch (vehicleType){
            case CAR:
                return new Cab(vehicleId, state);
        }
        return null;
    }
}
