package models;

import enums.CabState;

import java.util.*;

public class Cab extends Vehicle {
    private String type; // For example, Sedan, SUV, etc.

    public Cab(String id, City currentCity, String type) {
        super(id, currentCity);
        this.type = type;
    }

    @Override
    public String getType() {
        return "Cab - " + type;
    }
}