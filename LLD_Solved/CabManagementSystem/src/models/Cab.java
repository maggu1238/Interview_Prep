package models;

import enums.VehicleState;

public class Cab extends AbstractVehicle {
    public Cab(String vehicleId, VehicleState state) {
        super(vehicleId, state);
    }
}