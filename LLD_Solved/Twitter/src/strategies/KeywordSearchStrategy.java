package strategies;
// KeywordSearchStrategy
import model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class KeywordSearchStrategy implements SearchStrategy {

    @Override
    public List<Tweet> search(List<Tweet> tweets, String keyword) {
        List<Tweet> result = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getContent().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(tweet);
            }
        }
        return result;
    }
}
