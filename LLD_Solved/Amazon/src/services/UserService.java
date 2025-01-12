package services;

import command.*;
import command.PlaceOrderCommand;
import command.RemoveFromCartCommand;
import enums.PaymentType;
import model.*;
import model.Payment.Payment;

import java.util.*;


public class UserService {
    Map<String, AddToCartCommand> AddCartCommands;
    Map<String, RemoveFromCartCommand> RemoveCartCommands;
    Map<String, PlaceOrderCommand> placeOrderCommands;

    public UserService() {

    }

    public void addToCart(User user, Product product, int quantity){
        AddToCartCommand addToCartCommand;

        if(!AddCartCommands.containsKey(user.getId())){
            addToCartCommand = new AddToCartCommand(user, product, quantity);
        }
        else{
            addToCartCommand = AddCartCommands.get(user.getId());
        }
        addToCartCommand.execute();
    }

    public void removeFromCart(User user, Product product){

        // Similar logic

        RemoveFromCartCommand removeFromCartCommand = new RemoveFromCartCommand(user, product);
        removeFromCartCommand.execute();
    }

    public void placeOrder(User user, PaymentType payment){
        PlaceOrderCommand placeOrderCommand = new PlaceOrderCommand(user, payment);
        placeOrderCommand.execute();
    }

    public void notifyForProduct(User user, ProductObservable productObservable){
        NotifyForProductCommand notifyForProductCommand = new NotifyForProductCommand(user, productObservable);
        notifyForProductCommand.execute();
    }
}