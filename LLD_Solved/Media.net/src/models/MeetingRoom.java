package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SplittableRandom;

public class MeetingRoom {

    private final int id;
    private String name;
    private Set<String> features;
    private List<Integer> reservationIds;
    private boolean available;

    public MeetingRoom(int id, String name, Set<String> features){
        this.id = id;
        this.name = name;
        this.features = features;
        this.available = true;
        this.reservationIds = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void addReservationId(int reservationId){
        reservationIds.add(reservationId);
    }

    public  void removeReservation(int reservationId){

        int index = 0;

        for(int reservation : reservationIds){
            if(reservation == reservationId){
                reservationIds.remove(index);
                break;
            }
            index++;
        }
    }

    public List<Integer> getAllReservations(){
            return reservationIds;
    }

    public boolean isMeetingRoomHavingFeatures(MeetingRoomFilter filter){
        if(filter.getHasProjector() != null && filter.getHasProjector() != features.contains("Projector")){
            return false;
        }

        if(filter.getHasWhiteBoard() != null && filter.getHasWhiteBoard() != features.contains("WhiteBoard")){
            return false;
        }

        return true;
    }

    private boolean isAvailable(){
        return  available;
    }

    public void setAvailable(boolean status){
        this.available = status;
    }

    public synchronized boolean reserve(){
        if(isAvailable()){
            setAvailable(false);
            return true;
        }

        return false;
    }

}
