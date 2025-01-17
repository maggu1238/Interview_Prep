package models;

public class PriceAlert {
    String userId;
    String stockSymbol;
    double targetPrice;
    boolean notifyAbove; // true: notify when price rises above, false: notify when price falls below

    public PriceAlert(String userId, String stockSymbol, double targetPrice, boolean notifyAbove) {
        this.userId = userId;
        this.stockSymbol = stockSymbol;
        this.targetPrice = targetPrice;
        this.notifyAbove = notifyAbove;
    }
}
