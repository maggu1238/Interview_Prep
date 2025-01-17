package models;

import java.util.HashMap;
import java.util.Map;

public class User {
    String userId;
    double cashBalance; // Cash balance available for trading
    Map<String, Integer> stockHoldings; // Stock symbol to number of shares owned

    public User(String userId, double cashBalance) {
        this.userId = userId;
        this.cashBalance = cashBalance;
        this.stockHoldings = new HashMap<>();
    }

    // Add stocks to holdings
    public void addStock(String symbol, int quantity) {
        stockHoldings.put(symbol, stockHoldings.getOrDefault(symbol, 0) + quantity);
    }

    // Remove stocks from holdings
    public boolean removeStock(String symbol, int quantity) {
        int currentQuantity = stockHoldings.getOrDefault(symbol, 0);
        if (currentQuantity < quantity) {
            return false; // Not enough stock to sell
        }
        stockHoldings.put(symbol, currentQuantity - quantity);
        if (stockHoldings.get(symbol) == 0) {
            stockHoldings.remove(symbol);
        }
        return true;
    }

    // Display user details
    public void displayUserInfo() {
        System.out.println("User ID: " + userId);
        System.out.println("Cash Balance: $" + cashBalance);
        System.out.println("Stock Holdings: " + stockHoldings);
    }

    public String getUserId() {
        return userId;
    }
}
