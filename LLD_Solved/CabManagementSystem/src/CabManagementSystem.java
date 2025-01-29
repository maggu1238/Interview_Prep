import dataLayer.CityRepository;
import dataLayer.VehicleRepository;
import enums.StrategyType;
import enums.VehicleState;
import enums.VehicleType;
import factories.BookingStrategyFactory;
import factories.VehicleFactory;
import models.Cab;
import models.City;
import models.Vehicle;
import models.VehicleStateHistory;
import services.CabService;
import services.CityService;
import strategies.BookingStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class CabManagementSystem {

    private static CabManagementSystem instance; // Singleton instance

    private final CityRepository cityRepository;
    private final VehicleRepository vehicleRepository;

    private final CabService cabService;
    private final CityService cityService;

    private final VehicleFactory vehicleFactory;

    private CabManagementSystem(CityRepository cityRepository, VehicleRepository vehicleRepository, StrategyType strategyType){
        this.cityRepository = cityRepository;
        this.vehicleRepository = vehicleRepository;

        BookingStrategyFactory bookingStrategyFactory = new BookingStrategyFactory();
        BookingStrategy bookingStrategy = bookingStrategyFactory.createBookingStrategy(strategyType);

        this.vehicleFactory = new VehicleFactory();

        this.cabService = new CabService(vehicleRepository, bookingStrategy);
        cityService = new CityService(cityRepository);

    }

    public static synchronized CabManagementSystem getInstance(CityRepository cityRepository, VehicleRepository vehicleRepository, StrategyType strategyType) {
        if (instance == null) {
            instance = new CabManagementSystem(cityRepository, vehicleRepository, strategyType);
        }
        return instance;
    }

    // done
    public void registerVehicle(String vehicleId, String newCityId, VehicleState state) {
        Vehicle vehicle = vehicleFactory.createVehicle(vehicleId, state, VehicleType.CAR);
        if(!cabService.registerVehicle(vehicle)){
            System.out.println("Vehicle not registered");
            return;
        }
        System.out.println("Vehicle added successfully with vehicleId->" + vehicle.getVehicleId());

        if(state == VehicleState.IDLE){
            vehicleRepository.getVehicle(vehicleId).setCityId(newCityId);
            cityService.addIdleCabToCity(newCityId, vehicleId);
        }
        else if(state == VehicleState.ON_TRIP){
            vehicleRepository.getVehicle(vehicleId).setCityId("");
        }

    }

    // done
    public void updateVehicleLocation(String vehicleId, String newCityId){
        cabService.updateVehicleLocation(vehicleId, newCityId);

        if(vehicleRepository.getVehicle(vehicleId).getState() == VehicleState.IDLE){
            cityService.addIdleCabToCity(newCityId, vehicleId);
        }
    }

    // done
    public void updateVehicleState(String vehicleId, VehicleState newState){
        cabService.updateVehicleState(vehicleId, newState);

        if(!vehicleRepository.getVehicle(vehicleId).getCityId().isEmpty()){
            if(newState == VehicleState.IDLE){
                cityService.addIdleCabToCity(vehicleRepository.getVehicle(vehicleId).getCityId(), vehicleId);
            }
        }
    }

    // done
    public Vehicle bookVehicle(String cityId){
        Vehicle vehicle = null;

        if(cityService.isCityOnboarded(cityId)){
            cityService.recordBookingReq(cityId);
            List<String> availableCabIds = cityService.getAllIdleCabIds(cityId);

            if (!availableCabIds.isEmpty()) {
                vehicle = cabService.getVehicleToBook(availableCabIds);

                String vehicleId = vehicle.getVehicleId();
                VehicleState vehicleState = VehicleState.ON_TRIP;

                updateVehicleState(vehicleId, vehicleState);

                cityService.removeIdleCabId(cityId, vehicle.getVehicleId());
            }
            else{
                System.out.println("No available vehicle found at city-> " + cityId +" not available");
                return null;
            }
        }
        else{
            System.out.println("Can't book a cab here. City with cityId->" + cityId + " is not onboarded");
            return  null;
        }

        System.out.println("Vehicle " + vehicle.getVehicleId() + " booked at cityId-> " + cityId);
        return vehicle;
    }

    // done
    public void onboardCity(String cityId, String name){
        cityService.onboardPlace(new City(cityId, name));
    }

    // done
    public Long findPeakDemandTime(String cityId){
        Long peakDemandtime = cityService.findPeakDemandTime(cityId);

        return peakDemandtime;
    }

    // done
    public List<String> findHighDemandCities(){
        return cityService.findHighDemandCities();
    }

    public long calculateIdleTime(String cabId, long start, long end) {
        return cabService.calculateIdleTime(cabId, start, end);
    }

    public List<VehicleState> getCabHistory(String cabId){

        List<VehicleState> cabHistory = new ArrayList<>();
        if(vehicleRepository.getVehicle(cabId) != null){
            cabHistory = cabService.getCabHistory(cabId);
        }
        System.out.println("Cab History with cabId-> " + cabId + " is " + cabHistory);

        return cabHistory;
    }

}
