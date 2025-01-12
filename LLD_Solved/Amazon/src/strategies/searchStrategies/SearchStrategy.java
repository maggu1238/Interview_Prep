package strategies.searchStrategies;

import model.Product;

import java.util.List;

public interface SearchStrategy {
    List<Product> search(String searchText, List<Product> products);
}
