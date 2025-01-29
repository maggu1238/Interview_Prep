package strategies;

import models.Vehicle;

import java.util.List;

public class IdleTimeBookingStrategy implements BookingStrategy {

    @Override
    public Vehicle selectCab(List<Vehicle> availableCabs) {
        Vehicle selectedCab = availableCabs.get(0);
        return selectedCab;
    }
}
