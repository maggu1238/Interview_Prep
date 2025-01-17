package models;

import java.util.*;

public class Order {
    String orderId;
    String symbol;
    double price;
    int quantity;
    OrderType type; // Buy or Sell
    long timestamp;
    String userId; // ID of the user who placed the order

    public Order(String orderId, String symbol, double price, int quantity, OrderType type, String userId) {
        this.orderId = orderId;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.userId = userId;
        this.timestamp = System.currentTimeMillis();
    }
}
