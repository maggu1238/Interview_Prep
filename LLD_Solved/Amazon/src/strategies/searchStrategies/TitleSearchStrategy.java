package strategies.searchStrategies;

import model.Product;

import java.util.List;

public class TitleSearchStrategy implements SearchStrategy {
    @Override
    public List<Product> search(String searchText, List<Product> products) {
        return List.of();
    }
}
