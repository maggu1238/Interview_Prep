package models;

public class Reservation {
    private final int reservId;
    private final int startTime;
    private final int endTime;
//    private final int userId;

    public Reservation(int reservId, int startTime, int endTime) {
        this.reservId = reservId;
        this.startTime = startTime;
        this.endTime = endTime;
//        this.userId = userId;
    }

    public int getReservId() {
        return reservId;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

//    public int getUserId() {
//        return userId;
//    }
}
