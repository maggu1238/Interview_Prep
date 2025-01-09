package strategies;

// SearchStrategy interface
import java.util.List;

interface SearchStrategy {
    List<Tweet> search(List<Tweet> tweets, String query);
}



