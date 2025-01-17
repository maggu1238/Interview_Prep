import models.*;
import models.OrderType;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        StockExchange exchange = new StockExchange();

        // Register users
        User user1 = new User("U1", 1000.00);
        User user2 = new User("U2", 500.00);
        user1.addStock("AAPL", 10); // User1 owns 10 shares of AAPL
        exchange.registerUser(user1);
        exchange.registerUser(user2);

        // Add price alerts
        exchange.addPriceAlert(new PriceAlert("U1", "AAPL", 50.00, false)); // Notify U1 if AAPL falls below $50
        exchange.addPriceAlert(new PriceAlert("U2", "AAPL", 60.00, true));  // Notify U2 if AAPL rises above $60

        // User1 places a sell order for AAPL
        Order sellOrder = new Order("S1", "AAPL", 55.00, 5, OrderType.SELL, "U1");
        exchange.placeOrder(sellOrder);

        // User2 places a buy order for AAPL
        Order buyOrder = new Order("B1", "AAPL", 55.00, 5, OrderType.BUY, "U2");
        exchange.placeOrder(buyOrder);

        // Display user info
        user1.displayUserInfo();
        user2.displayUserInfo();
    }
}
