//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        TwitterSystem twitterSystem = new TwitterSystem();

        // Get services (Only TweetService manages likes)
        UserService userService = twitterSystem.getUserService();
        TweetService tweetService = twitterSystem.getTweetService();
        NewsFeedService newsFeedService = twitterSystem.getNewsFeedService();

        // Register users
        User alice = userService.registerUser("Alice");
        User bob = userService.registerUser("Bob");

        // Follow a user
        alice.follow(bob.getId());

        // Post tweets
        Tweet tweet1 = tweetService.postTweet(alice, "Hello Twitter!");
        Tweet tweet2 = tweetService.postTweet(bob, "Good morning, world!");

        // Like a tweet (Must go through TweetService)
        tweetService.likeTweet(tweet1.getId(), bob.getId());

        // Generate and display Alice’s news feed
        System.out.println("News Feed for " + alice.getName() + ":");
        for (Tweet tweet : newsFeedService.getNewsFeed(alice)) {
            System.out.println(tweet.getContent() + " (Likes: " + twitterSystem.getLikeService().getLikeCount(tweet.getId()) + ")");
        }
    }
}