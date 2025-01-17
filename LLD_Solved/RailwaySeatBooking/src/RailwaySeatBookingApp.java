import java.util.*;

class RailwaySeatBookingApp {
    public static void main(String[] args) {
        // Initialize route and seat data
        Route route = new Route("R1", Arrays.asList("A", "B", "C", "D"));
        route.initializeSeats(10); // 10 seats for the route

        BookingService bookingService = new BookingService();

        // Book a seat from A to B
        System.out.println(bookingService.bookSeat(route, "User1", "A", "B")); // Booking confirmed

        // Try booking the same seat from A to C (should fail)
        System.out.println(bookingService.bookSeat(route, "User2", "A", "C")); // Booking failed

        // Book the seat from B to C
        System.out.println(bookingService.bookSeat(route, "User3", "B", "C")); // Booking confirmed

        // Release seat from A to B after completion
        System.out.println(bookingService.releaseSeat(route, "User1", "A", "B")); // Seat released

        // Book the seat from A to C (should succeed now)
        System.out.println(bookingService.bookSeat(route, "User2", "A", "C")); // Booking confirmed
    }
}

class Route {
    String routeId;
    List<String> stops;
    Map<String, Seat> seats = new HashMap<>();

    public Route(String routeId, List<String> stops) {
        this.routeId = routeId;
        this.stops = stops;
    }

    public void initializeSeats(int totalSeats) {
        for (int i = 1; i <= totalSeats; i++) {
            String seatId = "S" + i;
            seats.put(seatId, new Seat(seatId, stops));
        }
    }

    public List<String> getSegments(String startStop, String endStop) {
        int startIndex = stops.indexOf(startStop);
        int endIndex = stops.indexOf(endStop);
        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
            throw new IllegalArgumentException("Invalid stops");
        }
        return stops.subList(startIndex, endIndex);
    }
}

class Seat {
    String seatId;
    Map<String, Boolean> availability; // Segment availability: key = "A->B", value = true/false

    public Seat(String seatId, List<String> stops) {
        this.seatId = seatId;
        this.availability = new HashMap<>();
        for (int i = 0; i < stops.size() - 1; i++) {
            String segment = stops.get(i) + "->" + stops.get(i + 1);
            availability.put(segment, true);
        }
    }

    public boolean isAvailable(List<String> segments) {
        for (String segment : segments) {
            if (!availability.getOrDefault(segment, false)) {
                return false;
            }
        }
        return true;
    }

    public void book(List<String> segments) {
        for (String segment : segments) {
            availability.put(segment, false);
        }
    }

    public void release(List<String> segments) {
        for (String segment : segments) {
            availability.put(segment, true);
        }
    }
}

class BookingService {
    public String bookSeat(Route route, String userId, String startStop, String endStop) {
        List<String> segments = route.getSegments(startStop, endStop);
        for (Seat seat : route.seats.values()) {
            if (seat.isAvailable(segments)) {
                seat.book(segments);
                return "Booking confirmed for seat " + seat.seatId + " from " + startStop + " to " + endStop;
            }
        }
        return "Booking failed: No available seats for " + startStop + " to " + endStop;
    }

    public String releaseSeat(Route route, String userId, String startStop, String endStop) {
        List<String> segments = route.getSegments(startStop, endStop);
        for (Seat seat : route.seats.values()) {
            if (seat.isAvailable(segments)) {
                return "Seat is already available for " + startStop + " to " + endStop;
            }
        }

        // Release the seat after completing the journey
        for (Seat seat : route.seats.values()) {
            seat.release(segments);
        }
        return "Seat released for " + startStop + " to " + endStop;
    }
}
