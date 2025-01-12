package factories;

import constants.CreditCardParameters;
import enums.PaymentType;
import model.Payment.Payment;
import strategies.paymentStrategies.CreditCardPayment;

public class PaymentFactory {
    public Payment createPayment(PaymentType paymentType){
        switch (paymentType){
            case CreditCard:
                return new Payment(new CreditCardPayment(new CreditCardParameters()));
        }
        return null;
    }
}
