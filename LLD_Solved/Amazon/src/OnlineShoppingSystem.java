import command.NotifyForProductCommand;
import enums.PaymentType;
import enums.SearchStrategyType;
import model.*;
import model.Order.ShoppingCart;
import model.User;
import services.InventoryManagementService;
import services.UserService;

import java.util.*;

public class OnlineShoppingSystem {
    private Map<String, User> users;

    private static OnlineShoppingSystem instance;
    private final UserService userService;
    private final InventoryManagementService inventoryManagementService;
    private final Map<String, Product> products;
    private final Map<String, ProductObservable> productObservableMap;


    private OnlineShoppingSystem() {
        this.products = new HashMap<>();
        this.productObservableMap = new HashMap<>();

        userService = new UserService();
        inventoryManagementService = new InventoryManagementService(this.products, this.productObservableMap);
    }

    public static synchronized OnlineShoppingSystem getInstance() {
        if (instance == null) {
            instance = new OnlineShoppingSystem();
        }
        return instance;
    }

    // Handle User Operations
    public void registerUser(User user) {
        if (users.containsKey(user.getId())) {
            throw new IllegalArgumentException("User with ID " + user.getId() + " already exists.");
        }
        users.put(user.getId(), user);
    }


    public User getUser(String userId) {
        return users.get(userId);
    }

    // Handle Product Operations
    public void addProduct(Product product, int quantity) {
        inventoryManagementService.addProduct(product, quantity);
    }

    public Product getProduct(String productId) {
        return inventoryManagementService.getProduct(productId);
    }

    public List<Product> searchProducts(String keyword, SearchStrategyType searchStrategyType) {
        return inventoryManagementService.searchProducts(keyword, searchStrategyType);
    }

    // Handle Cart Operations (Internal Command Handling)
    public void addToCart(User user, Product product, int quantity) {
        userService.addToCart(user, product, quantity);
    }

    public void removeFromCart(User user, String productId) {
        userService.removeFromCart(user, products.get(productId));
    }

    public ShoppingCart viewCart(User user) {
       return user.getCart();
    }

    public void initiateOrder(User user, PaymentType paymentType) {
        userService.placeOrder(user, paymentType );
    }

    public void notifyForProduct(User user, Product product){
        if(!productObservableMap.containsKey(product.getId())){
            productObservableMap.put(product.getId(), new ProductObservable(product));
        }
        ProductObservable  productObservable = productObservableMap.get(product.getId());
        userService.notifyForProduct(user, productObservable);
    }
}
