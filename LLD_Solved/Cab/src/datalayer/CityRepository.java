package datalayer;
package dataLayer;

import models.Cab;
import models.City;
import models.Vehicle;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CityRepository {
    private final Map<String, City> onBoardedCities;
    private final Map<String, List<LocalDateTime>> cityBookingRequests;
    private final Map<String, List<String>> cityToIdleCabIdsMap;

//    private static final Logger logger = Logger.getLogger(CityRepository.class.getName());

    public CityRepository(){
        onBoardedCities = new HashMap<>();
        cityBookingRequests = new HashMap<>();
        cityToIdleCabIdsMap = new HashMap<>();
    }

    public List<String> getAllIdleCabIds(String cityId){
        if (!cityToIdleCabIdsMap.containsKey(cityId)) {
//            logger.log(Level.SEVERE, "City with ID {0} does not exist in the cities database.", cityId);
            throw new NoSuchElementException("City with ID " + cityId + " does not exist.");
        }

//        LOGGER.log(Level.INFO, "Retrieved bookings for city ID {0}: {1}", new Object[]{cityId, cityToIdleCabIdsMap.get(cityId)});
        return cityToIdleCabIdsMap.get(cityId);
    }

    public void addIdleCabToCity(String cityId, String CabId){
        cityToIdleCabIdsMap.putIfAbsent(cityId, new ArrayList<>());

        cityToIdleCabIdsMap.get(cityId).add(CabId);
        //LOGGER.log(Level.INFO, "Cab added to city");
    }

    public void recordCityBookingReq(String cityId) {
        if (!isCityOnboarded(cityId)) {
            //LOGGER.log(Level.SEVERE, "City with ID {0} is not onboarded. Cannot record booking.", cityId);
            throw new IllegalArgumentException("City with ID " + cityId + " is not onboarded.");
        }

        cityBookingRequests.putIfAbsent(cityId, new ArrayList<>());
        cityBookingRequests.get(cityId).add(LocalDateTime.now());
        //LOGGER.log(Level.INFO, "Booking recorded for city ID {0} at {1}", new Object[]{cityId, LocalDateTime.now()});
    }

    public void onboardCity(City city){
        if (onBoardedCities.containsKey(city.getCityId())) {
            //LOGGER.log(Level.WARNING, "City with ID {0} is already onboarded.", city.getCityId());
        }

        onBoardedCities.put(city.getCityId(), city);
        //LOGGER.log(Level.INFO, "City with ID {0} onboarded successfully.", city.getCityId());
    }

    public boolean isCityOnboarded(String cityId){
        boolean isOnboarded = onBoardedCities.containsKey(cityId);
        //LOGGER.log(Level.INFO, "City with ID {0} onboarded status: {1}", new Object[]{cityId, isOnboarded});
        return isOnboarded;
    }

    public List<LocalDateTime> getBookingsRequestsByCityId(String cityId){
        if (!cityBookingRequests.containsKey(cityId)) {
            //LOGGER.log(Level.SEVERE, "City with ID {0} does not exist in the bookings database.", cityId);
            throw new NoSuchElementException("City with ID " + cityId + " does not exist.");
        }

        //LOGGER.log(Level.INFO, "Retrieved bookings for city ID {0}: {1}", new Object[]{cityId, cityBookingRequests.get(cityId)});
        return cityBookingRequests.get(cityId);
    }

    public Map<String, List<LocalDateTime>> getBookingRequestsForAllCities(){
        //LOGGER.log(Level.INFO, "Retrieved bookings for all cities.");
        return cityBookingRequests;
    }

}
