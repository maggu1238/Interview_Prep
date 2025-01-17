package models;

import java.time.LocalTime;

public class Platform {
    int platformId;
    LocalTime departureTime;

    public Platform(int platformId) {
        this.platformId = platformId;
        this.departureTime = LocalTime.MIN; // Initially free
    }

    public boolean isAvailable(LocalTime arrivalTime) {
        return arrivalTime.isAfter(departureTime) || arrivalTime.equals(departureTime);
    }

    public void allocateTrain(Train train) {
        this.departureTime = train.getDepartureTime();
        System.out.println("Platform " + platformId + " allocated to Train " + train.trainId +
                " (Departure: " + departureTime + ")");
    }
}
