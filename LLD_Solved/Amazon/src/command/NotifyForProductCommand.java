package command;

import model.Product;
import model.*;

import java.util.*;

public class NotifyForProductCommand implements Command {

   private ProductObservable productObservable;
   private User user;

    public NotifyForProductCommand(User user, ProductObservable productObservable) {
        this.productObservable = productObservable;
    }

    @Override
    public void execute() {
        productObservable.addObserver(user);
    }
}

