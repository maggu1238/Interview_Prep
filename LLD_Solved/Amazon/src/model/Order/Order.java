package model.Order;

import enums.OrderStatus;
import model.User;

import java.util.List;



public class Order {
    private String id;
    private User user;
    private List<OrderItem> items;
    private double totalAmount;
    private OrderStatus status;

    // Constructor
    public Order(String id, User user, List<OrderItem> items) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.totalAmount = calculateTotalAmount();
        this.status = OrderStatus.PENDING; // Default status is PENDING
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
        this.totalAmount = calculateTotalAmount(); // Recalculate total when items change
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // Calculate total order amount
    private double calculateTotalAmount() {
        return items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}
