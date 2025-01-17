import models.*;

import java.util.*;

import java.util.*;

class StockExchange {
    Map<String, OrderBook> orderBooks; // Key is the stock symbol
    Map<String, User> users; // Key is the user ID
    Map<String, List<PriceAlert>> priceAlerts; // Key is the stock symbol

    public StockExchange() {
        this.orderBooks = new HashMap<>();
        this.users = new HashMap<>();
        this.priceAlerts = new HashMap<>();
    }

    // Register a new user
    public void registerUser(User user) {
        users.put(user.getUserId(), user);
    }

    // Place an order
    public void placeOrder(Order order) {
        User user = users.get(order.getUserId());
        if (user == null) {
            System.out.println("User not found: " + order.getUserId());
            return;
        }

        if (order.getType() == OrderType.BUY) {
            double requiredCash = order.getPrice() * order.getQuantity();
            if (user.getCashBalance() < requiredCash) {
                System.out.println("Insufficient cash balance for user: " + order.getUserId());
                return;
            }
        } else if (order.getType() == OrderType.SELL) {
            if (!user.removeStock(order.getSymbol(), order.geQuantity())) {
                System.out.println("Insufficient stock holdings for user: " + order.getUserId());
                return;
            }
        }

        OrderBook orderBook = orderBooks.computeIfAbsent(order.getSymbol(), k -> new OrderBook(k));

        // Add the order to the order book
        if (order.getType() == OrderType.BUY) {
            orderBook.addBuyOrder(order);
        } else {
            orderBook.addSellOrder(order);
        }

        // Attempt to match orders
        List<Trade> trades = orderBook.matchOrders();
        for (Trade trade : trades) {
            processTrade(trade);
        }
    }

    // Process a trade
    private void processTrade(Trade trade) {
        User buyer = users.get(trade.buyOrder.userId);
        User seller = users.get(trade.sellOrder.userId);

        double totalPrice = trade.quantity * trade.price;

        // Update buyer's cash balance and stock holdings
        buyer.cashBalance -= totalPrice;
        buyer.addStock(trade.buyOrder.symbol, trade.quantity);

        // Update seller's cash balance and stock holdings
        seller.cashBalance += totalPrice;

        System.out.println("Trade executed: " + trade.quantity + " shares of " + trade.buyOrder.symbol +
                " at $" + trade.price + " (Buyer: " + buyer.userId + ", Seller: " + seller.userId + ")");

        // Notify users if the price triggers any alerts
        notifyUsers(trade.buyOrder.symbol, trade.price);
    }

    // Add a price alert for a user
    public void addPriceAlert(PriceAlert alert) {
        priceAlerts.computeIfAbsent(alert.stockSymbol, k -> new ArrayList<>()).add(alert);
        System.out.println("Price alert added for user " + alert.userId + " on stock " + alert.stockSymbol +
                " at target price " + alert.targetPrice);
    }

    // Notify users based on price alerts
    private void notifyUsers(String stockSymbol, double currentPrice) {
        List<PriceAlert> alerts = priceAlerts.getOrDefault(stockSymbol, new ArrayList<>());
        List<PriceAlert> triggeredAlerts = new ArrayList<>();

        for (PriceAlert alert : alerts) {
            boolean isTriggered = alert.notifyAbove ? (currentPrice >= alert.targetPrice) : (currentPrice <= alert.targetPrice);
            if (isTriggered) {
                System.out.println("Notification: User " + alert.userId + " - Stock " + alert.stockSymbol +
                        " has reached target price " + alert.targetPrice + " (Current Price: " + currentPrice + ")");
                triggeredAlerts.add(alert);
            }
        }

        // Remove triggered alerts
        alerts.removeAll(triggeredAlerts);
    }
}
