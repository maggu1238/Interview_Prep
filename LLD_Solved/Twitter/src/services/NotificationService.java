package services;

import model.Tweet;
import model.User;

import java.util.List;

public class NotificationService {


    // Notify Followed User about a New Follower
    public void notifyFollowedUser(User followee, User follower) {
        followee.followUpdate(follower);
    }

    // Notify Followers about a New Tweet
    public void notifyTweetFollowers(User user1, Tweet content, List<User> followers) {
        for (User user : followers) {
            user.postUpdate(user1);
        }
    }

    // Notify Commenter about Their Comment
    public void notifyCommenter(User commenter, Tweet tweet) {
        System.out.println("[Comment Notification] " + commenter.getName() + ": You commented on the tweet: " + tweet.getContent());
    }
}