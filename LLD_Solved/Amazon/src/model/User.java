package model;

import model.Order.Order;
import model.Order.ShoppingCart;

import java.util.List;

public class User implements Observer {
    private String id;
    private String name;
    private String email;
    private String password;
    private ShoppingCart cart;
    private List<Order> orders;

    // Constructor
    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cart = new ShoppingCart();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    // Method to authenticate the user based on password
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    // Method to add order
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public void update(String msg) {
        System.out.println(msg);
    }
}
