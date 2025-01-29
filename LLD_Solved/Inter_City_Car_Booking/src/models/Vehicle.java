package models;

import enums.CabState;

import java.util.*;

abstract public class Vehicle implements  I {
    private String id;
    private CabState state;
    private City currentCity;
    private long lastIdleTimestamp;
    private List<String> history;

    public Vehicle(String id, City currentCity) {
        this.id = id;
        this.state = CabState.IDLE;
        this.currentCity = currentCity;
        this.lastIdleTimestamp = System.currentTimeMillis();
        this.history = new ArrayList<>();
        history.add("IDLE at " + new Date(this.lastIdleTimestamp));
    }

    public String getId() {
        return id;
    }

    public CabState getState() {
        return state;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public long getLastIdleTimestamp() {
        return lastIdleTimestamp;
    }

    public List<String> getHistory() {
        return history;
    }

    public void changeState(CabState newState) {
        if (state == newState) return;
        history.add(newState + " at " + new Date());
        if (newState == CabState.IDLE) {
            this.lastIdleTimestamp = System.currentTimeMillis();
        }
        this.state = newState;
    }

    public void updateLocation(City city) {
        this.currentCity = city;
    }

    public abstract String getType();
}