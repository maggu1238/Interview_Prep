package dataLayer;

import constants.Constants;
import models.City;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CityRepository {
    private final Map<String, City> onBoardedCities;
    private final Map<String, List<Long>> cityBookingRequests;
    private final Map<String, List<String>> cityToIdleCabIdsMap;

    private static final Logger logger = Logger.getLogger(CityRepository.class.getName());

    public CityRepository(){
        onBoardedCities = new HashMap<>();
        cityBookingRequests = new HashMap<>();
        cityToIdleCabIdsMap = new HashMap<>();
    }

    public List<String> getAllIdleCabIds(String cityId){
        if (!cityToIdleCabIdsMap.containsKey(cityId)) {
            logger.log(Level.SEVERE, "City with ID {0} does not exist in the cities database.", cityId);
            throw new NoSuchElementException("City with ID " + cityId + " does not exist.");
        }

        return cityToIdleCabIdsMap.get(cityId);
    }

    public void addIdleCabToCity(String cityId, String CabId){
        cityToIdleCabIdsMap.putIfAbsent(cityId, new ArrayList<>());

        cityToIdleCabIdsMap.get(cityId).add(CabId);
    }

    public void recordCityBookingReq(String cityId) {
        if (!isCityOnboarded(cityId)) {
            logger.log(Level.SEVERE, "City with ID {0} is not onboarded. Cannot record booking.", cityId);
            throw new IllegalArgumentException("City with ID " + cityId + " is not onboarded.");
        }

        cityBookingRequests.putIfAbsent(cityId, new ArrayList<>());
        long time = System.currentTimeMillis();

        cityBookingRequests.get(cityId).add(time);
    }

    public void onboardCity(City city){
        if (onBoardedCities.containsKey(city.getCityId())) {
            logger.log(Level.WARNING, "City with ID {0} is already onboarded.", city.getCityId());
            return;
        }

        onBoardedCities.put(city.getCityId(), city);
    }

    public boolean isCityOnboarded(String cityId){
        boolean isOnboarded = onBoardedCities.containsKey(cityId);
        return isOnboarded;
    }

    public List<Long> getBookingsRequestsByCityId(String cityId){
        if (!cityBookingRequests.containsKey(cityId)) {
            logger.log(Level.SEVERE, "City with ID {0} does not exist in the bookings database.", cityId);
            throw new NoSuchElementException("City with ID " + cityId + " does not exist.");
        }

        return cityBookingRequests.get(cityId);
    }

    public Map<String, List<Long>> getBookingRequestsForAllCities(){
        return cityBookingRequests;
    }

}
