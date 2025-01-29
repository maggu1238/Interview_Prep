package strategies;

import models.Vehicle;

import java.util.List;

public interface BookingStrategy {
    Vehicle selectCab(List<Vehicle> availableCabs);
}