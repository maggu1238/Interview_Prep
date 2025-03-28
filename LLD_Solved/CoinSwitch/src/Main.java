enum DriverStatus {
    Available, On_trip
}

abstract  Person{
    string userID;
    string name;
    double rating;
    int noOfRating;
    Location location;
}

class  user extends Person{



    setLocation()
    getLocation()
    getRating()
    setRating(double rating){

    }
}

class vechile{
    string vehicleId;
}

class car extends vehicle{

    double farePerKm;

}


class bike extends vehicle{

    double farePerKm;

}

class Location{
    double x;
    double y;
}



class Driver {
    Vehicle vehicle
    setLocation()
    getLocation()
    getRating()
    setRating(double rating){

    }

    DriverStatus getStatus()
        SetStatus(Driver status)
}

class Ride {
    string rideId;
    string userId;
    string driverId;
    RideStatus rideStatus;
    double fare;
    int otp;


    setStatus(RideStatus status)
    getStatus()
    getFare()
}

abstract  PaymentDetails{
    string userId;

}

creditPaymentDetails {
    int cardNumber;
    int cvv;
}
interface bookingStrategy{
    void bookCar(list<Car>);
}

class nearestCar implements  bookingStrategy{

}

interface paymentStrategy{
    void payment(double fare, PaymentDetails paymentDetails);
}

CreditCardStrategy implements paymentStrategy{
    @Override
            payment(amount, paymentDetails)
}

PaymentFactory(){
    getPaymentStrtegy(paymentType)
        returns paymentStrategy
}


CabSystem {

    addUser(){

    }
    addDriver(){

    }

    bookCab(){
        //getAvailableCabs

        //creates a ride

        returns otp;
    }

    private getAllAvailableCabs(){

    }

    startRide(rideId, OTP){
        setDriverStatus
        setRideStatus

    }

    cancelRide(rideId){

    }


    endRide(){


    }

    payRide(paymentType, rideId){
        fare = ride.getFare();
        strategy = paymentFactory.getPaymentStrategy(paymentType);
        paymentDetailsFactory.getPaymentDetails(paymentType);
        strategy.payment();

    }
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