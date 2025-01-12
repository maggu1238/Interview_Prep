package model.Order;

import model.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<OrderItem> items;

    // Constructor
    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    // Getters and Setters
    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    // Method to add product to cart
    public void addProduct(Product product, int quantity) {
        // Check if product already exists in cart, update quantity if it does
        for (OrderItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        // Otherwise, add a new order item
        items.add(new OrderItem(product, quantity));
    }

    // Method to remove product from cart
    public boolean removeProduct(Product product) {
        return items.removeIf(item -> item.getProduct().equals(product));
    }

    // Method to calculate the total price of items in the cart
    public double calculateTotal() {
        return items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    // Method to check if the cart is empty
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Method to clear the cart
    public void clear() {
        items.clear();
    }
}
