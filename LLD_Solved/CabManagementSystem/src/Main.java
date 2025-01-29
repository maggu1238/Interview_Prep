import constants.Constants;
import dataLayer.CityRepository;
import dataLayer.VehicleRepository;
import enums.StrategyType;
import enums.VehicleState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        CityRepository cityRepository = new CityRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();

        Constants.startTime = System.currentTimeMillis();
        //System.out.println(System.currentTimeMillis());
        CabManagementSystem cabManagementSystem = CabManagementSystem.getInstance(cityRepository, vehicleRepository, StrategyType.Idle);

        //onboard  cityId,  cityName
        //register -> vehicleId ,  cityId,   state
        //book -> cityId

        // cabhistory -> cabId

        // updateVehicleLocation -> vehicleId,  Loc
        // updateVehicleState -> vehicle,  state
        // highDemandCity
        // peakDemandTime

        Thread thread = new Thread();

        cabManagementSystem.registerVehicle("123", "", VehicleState.ON_TRIP);
        cabManagementSystem.registerVehicle("124", "CityA", VehicleState.IDLE);
        cabManagementSystem.registerVehicle("125", "", VehicleState.ON_TRIP);
        cabManagementSystem.registerVehicle("126", "", VehicleState.ON_TRIP);
        cabManagementSystem.registerVehicle("127", "CityC", VehicleState.IDLE);
        cabManagementSystem.registerVehicle("128", "", VehicleState.ON_TRIP);
        cabManagementSystem.registerVehicle("129", "CityC", VehicleState.IDLE);
        sleepThread(thread);
        cabManagementSystem.onboardCity("CityA", "ABC");
        cabManagementSystem.onboardCity("CityB", "ABC1");
        //cabManagementSystem.onboardCity("CityC", "ABC2");
        cabManagementSystem.onboardCity("CityD", "ABC3");
        cabManagementSystem.onboardCity("CityE", "ABC4");

        //System.out.println(System.currentTimeMillis());
        cabManagementSystem.bookVehicle("CityA");
        sleepThread(thread);

        cabManagementSystem.getCabHistory("124");

        sleepThread(thread);

        cabManagementSystem.updateVehicleState("124", VehicleState.IDLE);
        sleepThread(thread);
        cabManagementSystem.updateVehicleLocation("124", "CityC");

        sleepThread(thread);
        cabManagementSystem.bookVehicle("CityC");



        cabManagementSystem.onboardCity("CityC", "ABC2");

        sleepThread(thread);
        cabManagementSystem.bookVehicle("CityC");
        cabManagementSystem.bookVehicle("CityC");
        cabManagementSystem.bookVehicle("CityC");
        cabManagementSystem.bookVehicle("CityC");


        cabManagementSystem.findPeakDemandTime("CityC");

        List<String> cities = cabManagementSystem.findHighDemandCities();

        sleepThread(thread);
        for(String city : cities){
           Long time =  cabManagementSystem.findPeakDemandTime(city);

           sleepThread(thread);
        }

        cabManagementSystem.bookVehicle("CityC");
        sleepThread(thread);
        cabManagementSystem.updateVehicleLocation("123", "CityC");
        sleepThread(thread);
        cabManagementSystem.updateVehicleState("123", VehicleState.IDLE);
        sleepThread(thread);
        cabManagementSystem.bookVehicle("CityC");
        sleepThread(thread);
        cabManagementSystem.bookVehicle("CityC");
        sleepThread(thread);
        cabManagementSystem.bookVehicle("CityC");
        cabManagementSystem.bookVehicle("CityC");
        cabManagementSystem.bookVehicle("CityC");
        cabManagementSystem.bookVehicle("CityC");
        cabManagementSystem.bookVehicle("CityC");
        cabManagementSystem.bookVehicle("CityC");
        cabManagementSystem.bookVehicle("CityC");



        cabManagementSystem.findPeakDemandTime("CityC");
       // System.out.println( cabManagementSystem.findPeakDemandTime("CityC"));

        cities = cabManagementSystem.findHighDemandCities();
        sleepThread(thread);



        for(String city : cities){
            Long time =  cabManagementSystem.findPeakDemandTime(city);

            sleepThread(thread);
        }

        cabManagementSystem.calculateIdleTime("124", Constants.startTime, System.currentTimeMillis());
        cabManagementSystem.calculateIdleTime("125", Constants.startTime, System.currentTimeMillis());
        cabManagementSystem.calculateIdleTime("126", Constants.startTime, System.currentTimeMillis());
        cabManagementSystem.calculateIdleTime("127", Constants.startTime, System.currentTimeMillis());
        cabManagementSystem.calculateIdleTime("128", Constants.startTime, System.currentTimeMillis());
        cabManagementSystem.calculateIdleTime("129", Constants.startTime, System.currentTimeMillis());
        cabManagementSystem.calculateIdleTime("130", Constants.startTime, System.currentTimeMillis());
    }

    public static void sleepThread(Thread thread){
        try {
            thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}