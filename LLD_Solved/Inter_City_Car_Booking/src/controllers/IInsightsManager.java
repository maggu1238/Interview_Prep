package controllers;

import models.*;

import java.util.*;

public interface IInsightsManager {
    long getIdleTime(Vehicle vehicle, long start, long end);

    Map<City, Integer> getDemandData(List<City> cities);
}
