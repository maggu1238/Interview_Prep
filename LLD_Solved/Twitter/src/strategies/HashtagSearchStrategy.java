package strategies;
// HashtagSearchStrategy
import model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class HashtagSearchStrategy implements SearchStrategy {

    @Override
    public List<Tweet> search(List<Tweet> tweets, String hashtag) {
        List<Tweet> result = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getHashtags().contains(hashtag.toLowerCase())) {
                result.add(tweet);
            }
        }
        return result;
    }
}