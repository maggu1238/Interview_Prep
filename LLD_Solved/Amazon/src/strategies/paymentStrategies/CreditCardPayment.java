package strategies.paymentStrategies;

import constants.CreditCardParameters;

public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String expirationDate;
    private String cvv;

    // Constructor
    public CreditCardPayment(CreditCardParameters parameters) {
        this.cardNumber = parameters.number;
        this.expirationDate = parameters.expiryDate;
        this.cvv = parameters.cvv;
    }

    @Override
    public boolean processPayment(double amount) {
        // Simulate credit card payment processing
        System.out.println("Processing credit card payment of $" + amount);
        // For the sake of simplicity, we'll assume the payment is always successful
        return true;
    }
}
