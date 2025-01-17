package models;

public class Trade {
    String tradeId;
    Order buyOrder;
    Order sellOrder;
    int quantity;
    double price;

    public Trade(String tradeId, Order buyOrder, Order sellOrder, int quantity, double price) {
        this.tradeId = tradeId;
        this.buyOrder = buyOrder;
        this.sellOrder = sellOrder;
        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}