package services;
import java.util.List;

public class TweetSearchService {

    private SearchStrategy searchStrategy;

    // Set the search strategy at runtime
    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    // Perform the search using the current strategy
    public List<Tweet> performSearch(TwitterSystem twitterSystem, String query) {
        return searchStrategy.search(twitterSystem.getAllTweets(), query);
    }
}
