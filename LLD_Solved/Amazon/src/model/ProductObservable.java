package model;

import java.util.ArrayList;
import java.util.List;

public class ProductObservable implements Observable {
    private final List<User> observers = new ArrayList<>();
    private String state; // Example state
    private Product product;

    public ProductObservable(Product product) {
        this.product = product;
    }

    @Override
    public void addObserver(User user) {
        observers.add(user);
    }

    @Override
    public void removeObserver(User user) {
        observers.remove(user);
    }

    @Override
    public void notifyObservers() {
        for (User observer : observers) {
            observer.update("State changed to: " + state);
            removeObserver(observer);
        }
    }

//    @Override
//    public void setState(String state) {
//        this.state = state;
//        notifyObservers(); // Notify observers whenever the state changes
//    }
//
//    @Override
//    public String getState() {
//        return state;
//    }
}
