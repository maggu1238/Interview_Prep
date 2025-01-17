package models;

import java.time.LocalTime;

public class Train {
    String trainId;
    LocalTime arrivalTime;
    int haltTime; // Halt time in minutes

    public Train(String trainId, LocalTime arrivalTime, int haltTime) {
        this.trainId = trainId;
        this.arrivalTime = arrivalTime;
        this.haltTime = haltTime;
    }

    public void updateArrivalTime(LocalTime newArrivalTime) {
        this.arrivalTime = newArrivalTime;
    }

    public LocalTime getDepartureTime() {
        return arrivalTime.plusMinutes(haltTime);
    }

    @Override
    public String toString() {
        return "Train{" +
                "trainId='" + trainId + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", haltTime=" + haltTime +
                '}';
    }
}
