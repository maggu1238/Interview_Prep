package models;

import enums.VehicleState;
import java.time.LocalDateTime;

public class VehicleStateHistory {
    private final VehicleState state;
    private final Long timestamp;

    public VehicleStateHistory(VehicleState state, Long timestamp) {
        this.state = state;
        this.timestamp = timestamp;
    }

    public VehicleState getState() {
        return state;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}