package command;

import model.Product;
import model.User;

public class RemoveFromCartCommand implements Command {
    private final User user;
    private final Product product;

    public RemoveFromCartCommand(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    @Override
    public void execute() {
        boolean removed = user.getCart().removeProduct(product);
        if (removed) {
            System.out.println("Product " + product.getId() + " removed from cart.");
        } else {
            System.out.println("Product not found in cart.");
        }
    }
}

