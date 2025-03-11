//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;

/** Represents a User in the system */
class User {
    private final String userId;
    private final String name;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
}

/** Represents a Tweet */
class Tweet {
    private final String tweetId;
    private final String userId;
    private final String content;
    private final long timestamp;

    public Tweet(String tweetId, String userId, String content) {
        this.tweetId = tweetId;
        this.userId = userId;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public String getTweetId() { return tweetId; }
    public String getUserId() { return userId; }
    public String getContent() { return content; }
    public long getTimestamp() { return timestamp; }
}

/** Manages user profiles and relationships */
class UserProfileService {
    private final Set<String> users;
    private final Map<String, Set<String>> followRelations;

    public UserProfileService(Set<String> users, Map<String, Set<String>> followRelations) {
        this.users = users;
        this.followRelations = followRelations;
    }

    public void createUser(String userId, String name) {
        users.add(userId);
        followRelations.putIfAbsent(userId, new HashSet<>());
    }

    public void followUser(String userId, String followeeId) {
        followRelations.putIfAbsent(userId, new HashSet<>());
        followRelations.get(userId).add(followeeId);
    }

    public void unfollowUser(String userId, String followeeId) {
        followRelations.getOrDefault(userId, new HashSet<>()).remove(followeeId);
    }

    public Set<String> getFollowees(String userId) {
        return followRelations.getOrDefault(userId, Collections.emptySet());
    }
}

/** Manages tweets (post, like, dislike) */
class PostService {
    private final Map<String, Tweet> tweets;
    private final Map<String, List<String>> userTweets;
    private final Map<String, Integer> likeTweetMap;

    public PostService(Map<String, Tweet> tweets,
                       Map<String, List<String>> userTweets,
                       Map<String, Integer> likeTweetMap) {
        this.tweets = tweets;
        this.userTweets = userTweets;
        this.likeTweetMap = likeTweetMap;
    }

    public String postTweet(String userId, String content) {
        String tweetId = UUID.randomUUID().toString();
        Tweet tweet = new Tweet(tweetId, userId, content);
        tweets.put(tweetId, tweet);
        userTweets.putIfAbsent(userId, new ArrayList<>());
        userTweets.get(userId).add(tweetId);
        likeTweetMap.putIfAbsent(tweetId, 0);
        return tweetId;
    }

    public void likeTweet(String tweetId) {
        if (tweets.containsKey(tweetId)) likeTweetMap.put(tweetId, likeTweetMap.get(tweetId) + 1);
    }

    public void dislikeTweet(String tweetId) {
        if (tweets.containsKey(tweetId)) likeTweetMap.put(tweetId, likeTweetMap.get(tweetId) - 1);
    }


    public List<Tweet> getTopKTweets(String userId, int k){
        // minHeap
        PriorityQueue<TweetWithLike> pq = new PriorityQueue<>(new LikeComparator());

        for( String tweetId : userTweets.get(userId)){
            TweetWithLike tweetWithLike = new TweetWithLike(tweetId, likeTweetMap.get(tweetId));

            pq.offer(tweetWithLike);
            if(pq.size() > k){
                pq.poll();
            }
        }
        List<Tweet> result = new ArrayList<>();

        while(!pq.isEmpty()){
            TweetWithLike  tweetWithLike = pq.poll();
            String tweetId = tweetWithLike.tweetId;
            result.add(tweets.get(tweetId));
        }
        return result;
    }

    private class LikeComparator implements Comparator<TweetWithLike>{

        @Override
        public int compare(TweetWithLike o1, TweetWithLike o2) {
            return Integer.compare(o1.likeCount, o2.likeCount);
        }
    }

    private class TweetWithLike{
        String tweetId;
        int likeCount;

        TweetWithLike(String tweetId, int likeCount){
            this.likeCount = likeCount;
            this.tweetId = tweetId;
        }
    }
}

/** Generates a timeline using shared data (decoupled from PostService & UserProfileService) */
class NewsFeedService {
    private final Map<String, Tweet> tweets;
    private final Map<String, List<String>> userTweets;
    private final Map<String, Set<String>> followRelations;

    public NewsFeedService(Map<String, Tweet> tweets, Map<String, List<String>> userTweets, Map<String, Set<String>> followRelations) {
        this.tweets = tweets;
        this.userTweets = userTweets;
        this.followRelations = followRelations;
    }

    public List<Tweet> getUserTimeline(String userId) {
        List<Tweet> timeline = new ArrayList<>();

        // Get user's own tweets
        for (String tweetId : userTweets.getOrDefault(userId, Collections.emptyList())) {
            timeline.add(tweets.get(tweetId));
        }

        // Get followees' tweets
        for (String followeeId : followRelations.getOrDefault(userId, Collections.emptySet())) {
            for (String tweetId : userTweets.getOrDefault(followeeId, Collections.emptyList())) {
                timeline.add(tweets.get(tweetId));
            }
        }

        // Sort tweets in reverse chronological order
        timeline.sort((a, b) -> Long.compare(b.getTimestamp(), a.getTimestamp()));
        return timeline;
    }
}

/** Twitter Service - Centralized data store & service injection */
class TwitterService {
    private final Set<String> users = new HashSet<>();
    private final Map<String, Set<String>> followRelations = new HashMap<>();
    private final Map<String, Tweet> tweets = new HashMap<>();
    private final Map<String, List<String>> userTweets = new HashMap<>();
    private final Map<String, Integer> likeTweetMap = new HashMap<>();

    private final UserProfileService userProfileService;
    private final PostService postService;
    private final NewsFeedService newsFeedService;

    public TwitterService() {
        // Initialize services with shared data
        this.userProfileService = new UserProfileService(users, followRelations);
        this.postService = new PostService(tweets, userTweets, likeTweetMap);
        this.newsFeedService = new NewsFeedService(tweets, userTweets, followRelations);
    }

    public void createUser(String userId, String name) {
        userProfileService.createUser(userId, name);
    }

    public void followUser(String userId, String followeeId) {
        userProfileService.followUser(userId, followeeId);
    }

    public void unfollowUser(String userId, String followeeId) {
        userProfileService.unfollowUser(userId, followeeId);
    }

    public String postTweet(String userId, String content) {
        return postService.postTweet(userId, content);
    }

    public void likeTweet(String tweetId) {
        postService.likeTweet(tweetId);
    }

    public void dislikeTweet(String tweetId) {
        postService.dislikeTweet(tweetId);
    }

    public List<Tweet> getUserTimeline(String userId) {
        return newsFeedService.getUserTimeline(userId);
    }
}

/** Test the implementation */
public class Main {
    public static void main(String[] args) {
        TwitterService twitterService = new TwitterService();

        // Create Users
        twitterService.createUser("1", "Alice");
        twitterService.createUser("2", "Bob");

        // Alice follows Bob
        twitterService.followUser("1", "2");

        // Bob posts a tweet
        String tweetId = twitterService.postTweet("2", "Hello, Twitter!");

        // Alice likes Bob's tweet
        twitterService.likeTweet(tweetId);

        // Fetch Alice's news feed
        List<Tweet> timeline = twitterService.getUserTimeline("1");
        for (Tweet tweet : timeline) {
            System.out.println("Tweet from " + tweet.getUserId() + ": " + tweet.getContent());
        }
    }
}
