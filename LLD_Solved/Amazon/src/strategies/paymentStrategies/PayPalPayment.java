package strategies.paymentStrategies;

public class PayPalPayment implements PaymentStrategy {
    private String paypalEmail;

    // Constructor
    public PayPalPayment(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    @Override
    public boolean processPayment(double amount) {
        // Simulate PayPal payment processing
        System.out.println("Processing PayPal payment of $" + amount + " using " + paypalEmail);
        // For the sake of simplicity, we'll assume the payment is always successful
        return true;
    }
}

