


//
// seach for events   list<events>   list of events of tht type
// view the events  <event torage
// book the event   < bookingsevice   -> user - > booking


// events -> any type  football, cricket, movie, standup
// user
// Seat  -> normal , premium
// address
// booking


//


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public Saet{
    private String seatId;

}

public class Location{
    private final String address;
    public  Location(String address){
        this.address =address;
    }
}


public class User{
    private final String userId;
    private final String name;
    private final String email;


    public User(String userId, String name, String email){
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}

abstract class Event{
    private final String eventId;
    private final Location location;
    private final String eventDate;
    private final Map<String, Se>
//    private final String type;

    public Event(String eventId, Location location, String eventData){

        this.eventId = eventId;
        this.location = location;
        this.eventDate = eventDate;
//        this.type = type;
    }

    public String getEventId() {
        return eventId;
    }

    public Location getLocation() {
        return location;
    }

    public String getEventDate() {
        return eventDate;
    }

    abstract String getType();
}

class Movie extends Event{

    private List<Integer> timeslots;
    public Movie(String eventId, Location location, String eventDate){
        super( eventId,  location, eventDate);
    }

    @Override
    String getType() {
        return "Movie";
    }
}


class SearchService{
    private List<Event> events;


    public SearchService(List<Event> events){
        this.events =  events;
    }

    public List<Event> searchByType(String type){
        List<Event> eventsList = new ArrayList<>();

        for( Event event : events){
            event.getType() == "Moviw"
        }
    }
}
    Map<String, Event> events;
 String booking(user, eventId, List<Seat> seats){

    Event event  = events.get(eventId);
    Map<String, Seat> eventSeats = event.getSeats();
     Lock lock = new ReentrantLock();
    for( Seat seat : ){




 }

}
//(type, name, location, date , )


//
//list of events
//user  list<bookingId>
//
//bookingId, booking


abstract event{
//    addresss
            // seatmap listof seats
    eventId
            starttime
            endtime
}


bookingsystem{
    <>
}
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
    }
}