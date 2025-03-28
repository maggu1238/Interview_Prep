//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


import models.MeetingRoomFilter;
import services.MeetingRoomService;

import java.util.Arrays;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

        MeetingRoomService meetingRoomService =  new MeetingRoomService();
        meetingRoomService.addRoom(1,"room1", new HashSet<>(Arrays.asList("Projector", "WhiteBoard")));
        meetingRoomService.addRoom(2,"room2", new HashSet<>(Arrays.asList("Projector", "WhiteBoard")));
        meetingRoomService.addRoom(3,"room3", new HashSet<>(Arrays.asList("Projector")));

        MeetingRoomFilter filter = new MeetingRoomFilter.Builder().setHasProjector(true).setHasWhiteboard(true).build();
        MeetingRoomFilter filter1 = new MeetingRoomFilter.Builder().setHasProjector(true).build();

        meetingRoomService.reserveRoom(0,9, filter);
        meetingRoomService.reserveRoom(0,8, filter);
        meetingRoomService.reserveRoom(5,11, filter1);
    }
}