package model.Payment;

import strategies.paymentStrategies.PaymentStrategy;

public class Payment {
    private PaymentStrategy paymentStrategy;

    // Constructor to set the payment strategy
    public Payment(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean processPayment(double amount) {
        return paymentStrategy.processPayment(amount);
    }
}