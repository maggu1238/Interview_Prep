package command;

import model.Product;
import model.User;

public class AddToCartCommand implements Command {
    private final User user;
    private final Product product;
    private final int quantity;

    public AddToCartCommand(User user, Product product, int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public void execute() {
        user.getCart().addProduct(product, quantity);
    }
}

