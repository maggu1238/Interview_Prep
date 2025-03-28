package services;
//findRoom()
//
//CapacityBased(){
//     min capacity
//        }
import models.MeetingRoom;
import models.MeetingRoomFilter;
import models.Reservation;

import java.util.*;

public class MeetingRoomService {

    private List<MeetingRoom> allRooms;
    private Map<Integer, Reservation> reservationMap;

    private int reservationIdCounter;


    public MeetingRoomService() {
        reservationMap = new HashMap<>();
        allRooms = new ArrayList<>();
        reservationIdCounter = 0;

    }

    public void addRoom(int id, String name, Set<String> features){
        MeetingRoom meetingRoom = new MeetingRoom(id, name, features);

        allRooms.add(meetingRoom);
    }

    public boolean reserveRoom(int startTime, int endTime, MeetingRoomFilter meetingRoomFilter){
        List<MeetingRoom> availableRooms = getAvailableRoomIds(meetingRoomFilter, startTime, endTime);

        for(MeetingRoom meetingRoom : availableRooms){
            if(meetingRoom.reserve()){
                meetingRoom.setAvailable(true);

                Reservation reservation = new Reservation(reservationIdCounter, startTime, endTime);
                meetingRoom.addReservationId(reservationIdCounter);

                reservationMap.put(reservationIdCounter, reservation);
                reservationIdCounter++;
                System.out.println("Room is booked " + meetingRoom.getId());
                return true;
            }
        }

        System.out.println(" no Room is booked ");
        return false;
    }

    private List<MeetingRoom> getAvailableRoomIds(MeetingRoomFilter meetingRoomFilter, int startTime, int endTime){

        List<MeetingRoom> availableRooms = new ArrayList<>();
        for( MeetingRoom meetingRoom : allRooms){
            if(meetingRoom.isMeetingRoomHavingFeatures(meetingRoomFilter) && isRoomAvailable(meetingRoom, startTime, endTime)){
                availableRooms.add(meetingRoom);
            }
        }

        return availableRooms;
    }

    private boolean isRoomAvailable(MeetingRoom meetingRoom, int startTime, int endTime){
        for(Integer reservationId : meetingRoom.getAllReservations()){
           if((startTime > reservationMap.get(reservationId).getStartTime() && startTime < reservationMap.get(reservationId).getEndTime())
               || (endTime > reservationMap.get(reservationId).getStartTime() && endTime < reservationMap.get(reservationId).getEndTime())){
               return false;
           }
        }
        return true;
    }
}
