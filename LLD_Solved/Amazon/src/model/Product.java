package model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private int reservedQuantity;
    private final Object lock = new Object();

    // Constructor
    public Product(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Methods
    public boolean isAvailable(int requestedQuantity) {
        synchronized (lock) {
            return (quantity - reservedQuantity) >= requestedQuantity;
        }
    }

    public void reserve(int requestedQuantity) {
        synchronized (lock) {
            if (!isAvailable(requestedQuantity)) {
                throw new IllegalStateException("Not enough stock available for product: " + name);
            }
            reservedQuantity += requestedQuantity;
        }
    }

    public void releaseReservation(int reservedQuantityToRelease) {
        synchronized (lock) {
            reservedQuantity -= reservedQuantityToRelease;
            if (reservedQuantity < 0) reservedQuantity = 0;
        }
    }

    public void confirmReservation(int reservedQuantityToConfirm) {
        synchronized (lock) {
            if (reservedQuantity < reservedQuantityToConfirm) {
                throw new IllegalStateException("Reservation inconsistency for product: " + name);
            }
            quantity -= reservedQuantityToConfirm;
            reservedQuantity -= reservedQuantityToConfirm;
        }
    }

    // Method to update quantity
    public void updateQuantity(int quantityChange) {
        this.quantity += quantityChange;
    }
}

