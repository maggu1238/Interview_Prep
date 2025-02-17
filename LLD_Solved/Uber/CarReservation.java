//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
// Online Java Compiler
// Use this editor to write, compile and run your Java code online
import java.util.*;

class Car{
    private String carId;

    public  Car(String carId){
        this.carId = carId;
    }

    public String getCarId(){
        return  carId;
    }
}

class Reservation{
    private  String carId;
    private int startDate;
    private int endDate;
    private String reservationId;

    public Reservation(String reservationId, String carId, int startDate, int endDate){
        this.carId  = carId;
        this.reservationId = reservationId;
        this.startDate =startDate;
        this.endDate = endDate;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getEndDate(){
        return  endDate;
    }
}

class ReservationComparator implements Comparator<Reservation>{
    @Override
    public int compare(Reservation o1, Reservation o2) {
        return Integer.compare(o1.getStartDate(), o2.getStartDate());
    }
}

class ReservationSystem{
    Map<String, Car> cardata;
    Map<String, Reservation> reservationMap;
    Map<String, TreeSet<Reservation>> carReservationMap;

    public ReservationSystem(){
        this.cardata = new HashMap<>();
        this.reservationMap = new HashMap<>();
        this.carReservationMap = new HashMap<>();
    }


    public int addCar(String carId){
        Car car = new Car(carId);
        if(cardata.containsKey(carId)){
            System.out.println("Car with same Id already available.");
            return 0;
        }

        cardata.putIfAbsent(carId, car);
        carReservationMap.putIfAbsent(carId, new TreeSet<Reservation>(new ReservationComparator()));
        return 1;
    }

    private boolean isCarAvailable(String carId, int startDate, int endDate){
        TreeSet<Reservation> carReservations = carReservationMap.get(carId);

        Reservation reservationFloor = carReservations.floor(new Reservation("", "", startDate, endDate));
        Reservation reservationCeiling = carReservations.ceiling(new Reservation("", "", startDate, endDate));

        if(reservationFloor != null && reservationFloor.getEndDate() < startDate){
            return  false;
        }

        if(reservationCeiling!= null &&  reservationFloor.getStartDate() < endDate){
            return  false;
        }

        return true;
    }

    public List<Car> getAllAvailableCars(int startDate, int endDate){
        List<Car> availableCars = new ArrayList<>();

        for(Map.Entry<String, Car> entry: cardata.entrySet()){
            String carId = entry.getKey();
            Car car = entry.getValue();

            if(isCarAvailable(carId, startDate, endDate)) {
                availableCars.add(car);
            }
        }

        return availableCars;
    }

    public Car reserveCar(int startDate, int endDate){
        List<Car> availableCars = getAllAvailableCars(startDate, endDate);
        if(availableCars.size() == 0){
            System.out.println("No car available");
            return  null;
        }

        Car car = availableCars.getFirst();

        String reservationId = UUID.randomUUID().toString();
        Reservation reservation = new Reservation(reservationId, car.getCarId(), startDate, endDate);

        reservationMap.put(reservationId, reservation);

        carReservationMap.get(car.getCarId()).add(reservation);

        System.out.println("car " + car.getCarId() + " is booked");

        return car;

    }


}

public class Main {
    public static void main(String[] args) {
        List<String> carIds = Arrays.asList("roomA", "roomB", "roomC");
        ReservationSystem reservationSystem =  new ReservationSystem();

        for (String carId : carIds){
            reservationSystem.addCar(carId);
        }

        reservationSystem.reserveCar(9, 10);
        reservationSystem.reserveCar(9, 10);
        reservationSystem.reserveCar(9, 10);
        reservationSystem.reserveCar(9, 10);
        reservationSystem.reserveCar(9, 10);
        reservationSystem.reserveCar(9, 10);
        reservationSystem.reserveCar(9, 10);



//        System.out.println(reservationSystem.reserveCar(9, 10));
//        System.out.println(reservationSystem.reserveCar(9, 10));
//        System.out.println(reservationSystem.reserveCar(9, 10));
//        System.out.println(reservationSystem.reserveCar(9, 10));
//        System.out.println(reservationSystem.reserveCar(9, 10)); // Should succeed
    }
}