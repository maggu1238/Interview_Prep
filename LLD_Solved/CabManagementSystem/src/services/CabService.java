package services;

import dataLayer.VehicleRepository;
import enums.VehicleState;
import models.Cab;
import models.Vehicle;
import models.VehicleStateHistory;
import strategies.BookingStrategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CabService implements IVehicleService  {
    private final VehicleRepository repository;
    private final BookingStrategy bookingStrategy;

    private Logger logger = Logger.getLogger(CabService.class.getName());

    public CabService(VehicleRepository repository, BookingStrategy bookingStrategy) {
        this.repository = repository;
        this.bookingStrategy = bookingStrategy;
    }

    @Override
    public boolean registerVehicle(Vehicle vehicle) {
        try {
            repository.addVehicle(vehicle);
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE,  e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public VehicleState getVehicleState(String vehicleId){
        return  repository.getVehicle(vehicleId).getState();
    }

    @Override
    public void updateVehicleLocation(String vehicleId, String newCityId) {
        Vehicle vehicle;
        try {
            vehicle = repository.getVehicle(vehicleId);
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Failed to retrieve vehicle: " + e.getMessage());
            return;
        }

        if (vehicle != null) {
            vehicle.setCityId(newCityId);
        }
    }

    @Override
    public void updateVehicleState(String vehicleId, VehicleState newState) {
        Vehicle vehicle;

        try {
            vehicle = repository.getVehicle(vehicleId);
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Failed to retrieve vehicle: " + e.getMessage());
            return;
        }

        if (vehicle != null) {
            vehicle.setState(newState);
        }
        repository.addVehicleHistory(vehicleId, newState);
    }

    @Override
    public Vehicle getVehicleToBook(List<String> availableCabIds) {
        Vehicle selectedCab = null;

        List<Vehicle> availableCabs = new ArrayList<>();
        for( String cabId : availableCabIds){
            availableCabs.add(repository.getVehicle(cabId));
        }

        selectedCab = bookingStrategy.selectCab(availableCabs);

        return selectedCab;
    }

    public long calculateIdleTime(String cabId, long start, long end) {
        long totalIdleTime = 0;

        if(repository.getVehicle(cabId) == null){
            System.out.println("Cab with cabId-> " +cabId + " is not registered");
            return totalIdleTime;
        }
        List<VehicleStateHistory> history = repository.getVehicleHistory(cabId);
        if (history == null || history.isEmpty()) return 0;


        long idleStart  = -1;

        for (VehicleStateHistory record : history) {
            Long timestamp = record.getTimestamp();
            VehicleState state = record.getState();


            if (timestamp < start) continue; // Skip entries before the range
            if (timestamp > end) break;       // Stop if we exceed the range

            if (state == VehicleState.IDLE) {
                idleStart = timestamp;
            } else if (state != VehicleState.IDLE && idleStart != -1) {
                // Use LocalDateTime's comparison methods
                totalIdleTime += timestamp - idleStart;
                idleStart = -1;
            }
        }

        if (idleStart != -1) { // If it was idle till the end of the range
            totalIdleTime += end - idleStart;
        }

        System.out.println("Cab with cabId->" + cabId + "was idle for total of " + totalIdleTime
                + "milliseconds" + "from starttime: " + start + "to endTime: " + end);
        return totalIdleTime;
    }

    public List<VehicleState> getCabHistory(String cabId){
        List<VehicleStateHistory> history = repository.getVehicleHistory(cabId);

        List<VehicleState> cabHistory = new ArrayList<>();
        for(VehicleStateHistory vehicleStateHistory : history){
            cabHistory.add(vehicleStateHistory.getState());
        }
        return cabHistory;
    }
}