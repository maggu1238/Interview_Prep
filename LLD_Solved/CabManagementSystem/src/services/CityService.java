package services;

import constants.Constants;
import dataLayer.CityRepository;
import models.City;
import models.Place;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CityService implements IPlaceService {
    private final CityRepository cityRepository;

    private Logger logger = Logger.getLogger(CityService.class.getName());

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }


    @Override
    public void recordBookingReq(String cityId){
        cityRepository.recordCityBookingReq(cityId);
    }

    @Override
    public void onboardPlace(Place city){
        try {
            cityRepository.onboardCity((City)city);
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Error onboarding city: " + e.getMessage());
        }
        System.out.println("City with ID " + city.getCityId() + " onboarded successfully.");

    }

    public boolean isCityOnboarded(String cityId){
        return cityRepository.isCityOnboarded(cityId);
    }

    public List<String> getAllIdleCabIds(String cityId){
        return cityRepository.getAllIdleCabIds(cityId);
    }

    public void removeIdleCabId(String cityId, String vehicleId){
        cityRepository.getAllIdleCabIds(cityId).remove(vehicleId);
    }

    public void addIdleCabToCity(String cityId, String cabId){
        cityRepository.addIdleCabToCity(cityId, cabId);
    }

    // assuming that we want the peak minute for that city
    public Long findPeakDemandTime(String cityId) {
        List<Long> bookings;

        try {
            bookings = cityRepository.getBookingsRequestsByCityId(cityId);
        } catch (NoSuchElementException e) {
            logger.log(Level.SEVERE,  e.getMessage());
            return null;
        }

        if (bookings == null || bookings.isEmpty()) {
            logger.log(Level.WARNING,  "No bookings found for city ID: " + cityId);
            return null;
        }

        // Map to count bookings per hour
        Map<Long, Integer> secondCounts = new HashMap<>();

        for (Long bookingtime : bookings) {
            long milliseconds = (bookingtime - Constants.startTime);
            long second = milliseconds/1000;

            int seconds = secondCounts.getOrDefault(second, 0);
            secondCounts.put(second, seconds + 1);
        }

        long peakSecond = -1;
        int maxCount = 0;

        for (Map.Entry<Long, Integer> entry : secondCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                peakSecond= entry.getKey();
            }
        }

        System.out.println("peak second is " + peakSecond + " for cityId-> " + cityId);
        return peakSecond;
    }

    public List<String> findHighDemandCities() {
        Map<String, List<Long>> allBookingReqs;

        try {
            allBookingReqs = cityRepository.getBookingRequestsForAllCities();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving booking data: " +  e.getMessage());
            return null;
        }

        // Handle null or empty data gracefully
        if (allBookingReqs == null || allBookingReqs.isEmpty()) {
            logger.log(Level.WARNING, "No booking data available.");

            return null;
        }

        List<String> highDemandCities = new ArrayList<>();

        int maxBookingReqs = 0;

        for (Map.Entry<String, List<Long>> entry : allBookingReqs.entrySet()) {
            int bookingreqs = entry.getValue().size();
            if (bookingreqs > maxBookingReqs) {
                maxBookingReqs = bookingreqs;
                highDemandCities = new ArrayList<>();
                highDemandCities.add(entry.getKey());
            }
            else if(bookingreqs == maxBookingReqs){
                highDemandCities.add(entry.getKey());
            }
        }

        if (highDemandCities.isEmpty()) {
            logger.log(Level.INFO, "No bookings found for any city.");
        }

        System.out.println("High demanding cities are " + highDemandCities);
        return highDemandCities;
    }

}
