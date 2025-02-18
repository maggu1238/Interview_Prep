
//The Movie class represents a movie with properties such as ID, title, description, and duration.
//The Theater class represents a theater with properties such as ID, name, location, and a list of shows.
//The Show class represents a movie show in a theater, with properties such as ID, movie, theater, start time, end time, and a map of seats.
//The Seat class represents a seat in a show, with properties such as ID, row, column, type, price, and status.
//The SeatType enum defines the different types of seats (normal or premium).
//The SeatStatus enum defines the different statuses of a seat (available or booked).
//The Booking class represents a booking made by a user, with properties such as ID, user, show, selected seats, total price, and status.
//The BookingStatus enum defines the different statuses of a booking (pending, confirmed, or cancelled).
//The User class represents a user of the booking system, with properties such as ID, name, and email.
//The MovieTicketBookingSystem class is the main class that manages the movie ticket booking system. It follows the Singleton pattern to ensure only one instance of the system exists.
//The MovieTicketBookingSystem class provides methods for adding movies, theaters, and shows, as well as booking tickets, confirming bookings, and cancelling bookings.
//Multi-threading is achieved using concurrent data structures such as ConcurrentHashMap to handle concurrent access to shared resources like shows and bookings.
//The MovieTicketBookingDemo class demonstrates the usage of the movie ticket booking system by adding movies, theaters, shows, booking tickets, and confirming or cancelling bookings.

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class Movie {
    private final String id;
    private final String title;
    private final String description;
    private final String genre;
    private final int durationInMinutes;

    public Movie(String id, String title, String description, String genre, int durationInMinutes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.durationInMinutes = durationInMinutes;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

}

class Theater {
    private final String id;
    private final String name;
    private final String location;
    private final List<Show> shows;

    public Theater(String id, String name, String location, List<Show> shows) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.shows = shows;
    }
}

class Show {
    private final String id;
    private final Movie movie;
    private final Theater theater;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Map<String, Seat> seats;

    public Show(String id, Movie movie, Theater theater, LocalDateTime startTime, LocalDateTime endTime, Map<String, Seat> seats) {
        this.id = id;
        this.movie = movie;
        this.theater = theater;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seats = seats;
    }

    public String getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Theater getTheater() {
        return theater;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Map<String, Seat> getSeats() {
        return seats;
    }
}

class User {
    private final String id;
    private final String name;
    private final String email;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}

enum SeatType {
    NORMAL,
    PREMIUM
}

enum SeatStatus {
    AVAILABLE,
    BOOKED,
    RESERVED,
}

abstract class Seat {
    private final String id;
    private final int row;
    private final int column;
    protected final double price;

    public Seat(String id, int row, int column, double price) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public abstract SeatType getType();  // Enforce seat type in subclasses

    public abstract double calculatePrice();
}

class NormalSeat extends Seat {
    public NormalSeat(String id, int row, int column, double basePrice) {
        super(id, row, column, basePrice);
    }

    @Override
    public SeatType getType() {
        return SeatType.NORMAL;
    }

    @Override
    public double calculatePrice() {
        return price;  // No additional cost for normal seats
    }
}

enum BookingStatus {
    PENDING,
    CONFIRMED,
    CANCELLED
}

class Booking {
    private final String id;
    private final User user;
    private final Show show;
    private final List<Seat> seats;
    private final double totalPrice;

    public Booking(String id, User user, Show show, List<Seat> seats, double totalPrice) {
        this.id = id;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Show getShow() {
        return show;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}

enum  SearchType{
    Title,
    Genre
}

interface MovieSearchStrategy {
    List<Movie> search(List<Movie> movies, String keyword);
}

class SearchByTitle implements MovieSearchStrategy {
    @Override
    public List<Movie> search(List<Movie> movies, String keyword) {
        List<Movie> results = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(keyword)) {
                results.add(movie);
            }
        }
        return results;
    }
}

class SearchByGenre implements MovieSearchStrategy {
    @Override
    public List<Movie> search(List<Movie> movies, String keyword) {
        List<Movie> results = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getGenre().equalsIgnoreCase(keyword)) {
                results.add(movie);
            }
        }
        return results;
    }
}


interface SearchService {
    List<Movie> searchMovies(List<Movie> movies, String keyword, SearchType searchType);
}

class MovieSearchService implements SearchService {
    private MovieSearchStrategy strategy;

    private static MovieSearchService instance;

    private MovieSearchService() {
    }

    public static synchronized MovieSearchService getInstance() {
        if (instance == null) {
            instance = new MovieSearchService();
        }
        return instance;
    }

    @Override
    public List<Movie> searchMovies(List<Movie> movies, String keyword, SearchType searchType) {

        if(searchType == SearchType.Genre){
            strategy = new SearchByGenre();
        }

        return strategy.search(movies, keyword);
    }
}

public class MovieTicketBookingSystem {
    private static MovieTicketBookingSystem instance;
    private final List<Movie> movies;
    private final List<Theater> theaters;
    private final Map<String, Show> shows;
    private final Map<String, Booking> bookings;
    private final Map<String, SeatStatus> seatStatusMap;
    private final Map<String, BookingStatus> bookingStatusMap;
    private final MovieSearchService movieSearchService;

    private static final String BOOKING_ID_PREFIX = "BKG";
    private static final AtomicLong bookingCounter = new AtomicLong(0);

    MovieTicketBookingSystem() {
        movies = new ArrayList<>();
        theaters = new ArrayList<>();
        shows = new ConcurrentHashMap<>();
        bookings = new ConcurrentHashMap<>();
        seatStatusMap = new HashMap<>();
        bookingStatusMap = new HashMap<>();
        movieSearchService = MovieSearchService.getInstance();
    }

    public static synchronized MovieTicketBookingSystem getInstance() {
        if (instance == null) {
            instance = new MovieTicketBookingSystem();
        }
        return instance;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addTheater(Theater theater) {
        theaters.add(theater);
    }

    public void addShow(Show show) {
        shows.put(show.getId(), show);
    }

    public List<Movie> searchMovies(List<Movie> movies, String keyWord, SearchType searchType){
       return movieSearchService.searchMovies(movies, keyWord, searchType);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Theater> getTheaters() {
        return theaters;
    }

    public Show getShow(String showId) {
        return shows.get(showId);
    }

    public synchronized Booking bookTickets(User user, Show show, List<Seat> selectedSeats) {
        if (areSeatsAvailable(show, selectedSeats)) {
            markSeatsAsReserved(show, selectedSeats);

            double totalPrice = calculateTotalPrice(selectedSeats);
            String bookingId = generateBookingId();
            Booking booking = new Booking(bookingId, user, show, selectedSeats, totalPrice);
            bookings.put(bookingId, booking);
            bookingStatusMap.put(bookingId, BookingStatus.PENDING);

            confirmBooking(bookingId);

            markSeatsAsBooked(show, selectedSeats);
            return booking;
        }
        return null;
    }

    private boolean areSeatsAvailable(Show show, List<Seat> selectedSeats) {
        for (Seat seat : selectedSeats) {
            Seat showSeat = show.getSeats().get(seat.getId());
            if (showSeat == null || seatStatusMap.get(showSeat.getId()) != SeatStatus.AVAILABLE) {
                return false;
            }
        }
        return true;
    }

    private void markSeatsAsBooked(Show show, List<Seat> selectedSeats) {
        for (Seat seat : selectedSeats) {
            Seat showSeat = show.getSeats().get(seat.getId());
            seatStatusMap.put(showSeat.getId(),SeatStatus.BOOKED);
        }
    }

    private void markSeatsAsReserved(Show show, List<Seat> selectedSeats) {
        for (Seat seat : selectedSeats) {
            Seat showSeat = show.getSeats().get(seat.getId());
            seatStatusMap.put(showSeat.getId(),SeatStatus.RESERVED);
        }
    }

    private double calculateTotalPrice(List<Seat> selectedSeats) {
        return selectedSeats.stream().mapToDouble(Seat::getPrice).sum();
    }

    private String generateBookingId() {
        long bookingNumber = bookingCounter.incrementAndGet();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return BOOKING_ID_PREFIX + timestamp + String.format("%06d", bookingNumber);
    }

    public synchronized void confirmBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null && booking.getStatus() == BookingStatus.PENDING) {
            booking.setStatus(BookingStatus.CONFIRMED);
            // Process payment and send confirmation
            // ...
        }
    }

    public synchronized void cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null && booking.getStatus() != BookingStatus.CANCELLED) {
            booking.setStatus(BookingStatus.CANCELLED);
            markSeatsAsAvailable(booking.getShow(), booking.getSeats());
            // Process refund and send cancellation notification
            // ...
        }
    }

    private void markSeatsAsAvailable(Show show, List<Seat> seats) {
        for (Seat seat : seats) {
            Seat showSeat = show.getSeats().get(seat.getId());
            seatStatusMap.put(showSeat.getId(),SeatStatus.BOOKED);
        }
    }
}

public class Main {

    public static void main() {
        MovieTicketBookingSystem bookingSystem = MovieTicketBookingSystem.getInstance();

        // Add movies
        Movie movie1 = new Movie("M1", "Movie 1", "Description 1", 120);
        Movie movie2 = new Movie("M2", "Movie 2", "Description 2", 135);
        bookingSystem.addMovie(movie1);
        bookingSystem.addMovie(movie2);

        // Add theaters
        Theater theater1 = new Theater("T1", "Theater 1", "Location 1", new ArrayList<>());
        Theater theater2 = new Theater("T2", "Theater 2", "Location 2", new ArrayList<>());
        bookingSystem.addTheater(theater1);
        bookingSystem.addTheater(theater2);

        // Add shows
        Show show1 = new Show("S1", movie1, theater1, LocalDateTime.now(), LocalDateTime.now().plusMinutes(movie1.getDurationInMinutes()), createSeats(10, 10));
        Show show2 = new Show("S2", movie2, theater2, LocalDateTime.now(), LocalDateTime.now().plusMinutes(movie2.getDurationInMinutes()), createSeats(8, 8));
        bookingSystem.addShow(show1);
        bookingSystem.addShow(show2);

        // Book tickets
        User user = new User("U1", "John Doe", "john@example.com");
        List<Seat> selectedSeats = Arrays.asList(show1.getSeats().get("1-5"), show1.getSeats().get("1-6"));
        Booking booking = bookingSystem.bookTickets(user, show1, selectedSeats);
        if (booking != null) {
            System.out.println("Booking successful. Booking ID: " + booking.getId());
            bookingSystem.confirmBooking(booking.getId());
        } else {
            System.out.println("Booking failed. Seats not available.");
        }

        // Cancel booking
        bookingSystem.cancelBooking(booking.getId());
        System.out.println("Booking canceled. Booking ID: " + booking.getId());
    }

    private static Map<String, Seat> createSeats(int rows, int columns) {
        Map<String, Seat> seats = new HashMap<>();
        for (int row = 1; row <= rows; row++) {
            for (int col = 1; col <= columns; col++) {
                String seatId = row + "-" + col;
                SeatType seatType = (row <= 2) ? SeatType.PREMIUM : SeatType.NORMAL;
                double price = (seatType == SeatType.PREMIUM) ? 150.0 : 100.0;
                Seat seat = new Seat(seatId, row, col, seatType);
                seats.put(seatId, seat);
            }
        }
        return seats;
    }


}