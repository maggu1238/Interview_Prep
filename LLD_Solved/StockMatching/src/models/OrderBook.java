package models;

import java.util.*;

public class OrderBook {
    String symbol;
    PriorityQueue<Order> buyOrders; // Buy orders are sorted by price (descending)
    PriorityQueue<Order> sellOrders; // Sell orders are sorted by price (ascending)

    public OrderBook(String symbol) {
        this.symbol = symbol;
        this.buyOrders = new PriorityQueue<>((a, b) -> Double.compare(b.price, a.price)); // Max-heap for buy orders
        this.sellOrders = new PriorityQueue<>((a, b) -> Double.compare(a.price, b.price)); // Min-heap for sell orders
    }

    // Match orders between buy and sell
    public List<Trade> matchOrders() {
        List<Trade> trades = new ArrayList<>();

        while (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
            Order buyOrder = buyOrders.peek();
            Order sellOrder = sellOrders.peek();

            // If buy price >= sell price, execute the trade
            if (buyOrder.price >= sellOrder.price) {
                int tradeQuantity = Math.min(buyOrder.quantity, sellOrder.quantity);
                double tradePrice = sellOrder.price; // The price at which the trade occurs

                // Create a trade
                Trade trade = new Trade(UUID.randomUUID().toString(), buyOrder, sellOrder, tradeQuantity, tradePrice);
                trades.add(trade);

                // Update the quantities of the orders
                buyOrder.quantity -= tradeQuantity;
                sellOrder.quantity -= tradeQuantity;

                // Remove completed orders from the book
                if (buyOrder.quantity == 0) {
                    buyOrders.poll();
                }
                if (sellOrder.quantity == 0) {
                    sellOrders.poll();
                }
            } else {
                break;
            }
        }

        return trades;
    }

    // Add buy order
    public void addBuyOrder(Order order) {
        buyOrders.offer(order);
    }

    // Add sell order
    public void addSellOrder(Order order) {
        sellOrders.offer(order);
    }
}
