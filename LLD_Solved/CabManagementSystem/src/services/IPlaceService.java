package services;

import models.City;
import models.Place;

import java.time.LocalDateTime;

public interface IPlaceService {
    void recordBookingReq(String placeId);
    void onboardPlace(Place place);
}
