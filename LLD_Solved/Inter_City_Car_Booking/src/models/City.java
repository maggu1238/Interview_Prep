package models;

import java.util.*;

public class City {
    private String id;
    private String name;

    // move separately
    private List<Cab> cabs;

    public City(String id, String name) {
        this.id = id;
        this.name = name;
        this.cabs = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Cab> getCabs() {
        return cabs;
    }

    public void addVehicle(Cab cab) {
        cabs.add(cab);
    }

    public void removeCab(Cab cab) {
        cabs.remove(cab);
    }
}