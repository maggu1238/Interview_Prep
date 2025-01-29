package models;

import enums.VehicleState;
import java.time.LocalDateTime;

public class VehicleStateHistory {
    private final VehicleState state;
    private final LocalDateTime timestamp;

    public VehicleStateHistory(VehicleState state, LocalDateTime timestamp) {
        this.state = state;
        this.timestamp = timestamp;
    }

    public VehicleState getState() {
        return state;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}