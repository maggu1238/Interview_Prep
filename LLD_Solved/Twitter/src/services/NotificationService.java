package services;

public class NotificationService {

    // Notify Followers about a New Tweet
    public void notifyTweetFollowers(User user, String content) {
        System.out.println("[Tweet Notification] " + user.getName() + ": " + content);
        // Notify each follower (iterate through user.getFollowers())
    }

    // Notify Followed User about a New Follower
    public void notifyFollowedUser(User followee, User follower) {
        System.out.println("[Follow Notification] " + followee.getName() + ": " + follower.getName() + " followed you.");
    }

    // Notify the Follower about Successful Follow
    public void notifyFollowee(User follower, User followee) {
        System.out.println("[Follow Notification] " + follower.getName() + ": You are now following " + followee.getName());
    }

    // Notify Tweet Owner about a Comment
    public void notifyTweetOwner(User tweetOwner, User commenter, Tweet tweet) {
        System.out.println("[Comment Notification] " + tweetOwner.getName() + ": " + commenter.getName() + " commented on your tweet: " + tweet.getContent());
    }

    // Notify Commenter about Their Comment
    public void notifyCommenter(User commenter, Tweet tweet) {
        System.out.println("[Comment Notification] " + commenter.getName() + ": You commented on the tweet: " + tweet.getContent());
    }
}