package services;

import enums.SearchStrategyType;
import factories.SearchStrategyFactory;
import model.Product;
import model.ProductObservable;
import strategies.searchStrategies.SearchStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryManagementService {
    private Map<String, Product> products;
    private Map<String, ProductObservable> productObservableMap;

    public InventoryManagementService(Map<String, Product> products, Map<String, ProductObservable> productObservableMap) {
        this.products = products;
        this.productObservableMap = productObservableMap;
    }

    public void addProduct(Product product, int quantity) {
        Product product1 = products.get(product.getId());

        if(product1 != null){
            if(product1.getQuantity() == 0){
                productObservableMap.get(product.getId()).notifyObservers();
            }
            product1.updateQuantity(quantity);
        }
        else{
            product1.updateQuantity(quantity);
            products.put(product.getId(), product1);
        }
    }

    public Product getProduct(String productId) {
        Product product = products.get(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found.");
        }
        return product;
    }

    public List<Product> searchProducts(String keyword, SearchStrategyType searchStrategyType) {
        SearchStrategyFactory searchStrategyFactory = new SearchStrategyFactory();
        SearchStrategy searchStrategy = searchStrategyFactory.createStrategy(searchStrategyType);
        return searchStrategy.search(keyword, new ArrayList<>(products.values()));
    }
}
