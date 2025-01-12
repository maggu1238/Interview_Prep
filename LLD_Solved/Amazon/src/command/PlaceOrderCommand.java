package command;

import enums.OrderStatus;
import enums.PaymentType;
import factories.PaymentFactory;
import java.util.*;
import model.*;
import model.Order.Order;
import model.Order.OrderItem;
import model.Payment.Payment;

public class PlaceOrderCommand  implements Command {

    private User user;
    private Payment payment;

    public PlaceOrderCommand(User user, PaymentType paymentType){
        this.user = user;

        // don't do this create the paymentMethods in main somewhere and pass the value as a parameter 
        // else you have to create the payment object everytime the placeOrder command is cllaed
        PaymentFactory paymentFactory = new PaymentFactory();
        this.payment = paymentFactory.createPayment(paymentType);
    }

    @Override
    public void execute() {
            List<OrderItem> orderItems = new ArrayList<>();

            // Step 1: Reserve quantities
            for (OrderItem item : user.getCart().getItems()) {
                Product product = item.getProduct();
                int quantity = item.getQuantity();

                // Reserve product quantities using product-level locking
                product.reserve(quantity);
                orderItems.add(item);
            }

            String orderId = generateOrderId();
            Order order = new Order(orderId, user, orderItems);
            user.addOrder(order);

        try {
                // Step 2: Process payment
                if (payment.processPayment(order.getTotalAmount())) {
                    order.setStatus(OrderStatus.PROCESSING);

                    // Step 3: Confirm the reservation
                    for (OrderItem item : orderItems) {
                        Product product = item.getProduct();
                        int quantity = item.getQuantity();

                        // Confirm product quantities using product-level locking
                        product.confirmReservation(quantity);
                    }
                    order.setStatus(OrderStatus.COMPLETED);
                    user.getCart().clear();
                } else {
                    throw new IllegalStateException("Payment failed");
                }
            } catch (Exception e) {
                // Step 4: Release reservations on failure
                for (OrderItem item : orderItems) {
                    Product product = item.getProduct();
                    int quantity = item.getQuantity();

                    // Release reserved quantities using product-level locking
                    product.releaseReservation(quantity);
                }

                order.setStatus(OrderStatus.CANCELLED);
                throw e; // Re-throw exception to indicate failure
            }
    }

    private String generateOrderId() {
        return "ORDER" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
