package services;

import strategies.TimelineStrategy;

import java.util.*;
import model.*;

public class UserService {
    private NotificationService notificationService;
    private TimelineService timelineService;
    private TweetSearchService tweetSearchService;

    private Map<String, User> userIdToUserMap;
    private Map<String, Tweet> tweetIdToTweetMap;
    private Map<String, List<String>> usersToTweetIdMap;
    private Map<String, List<String>> followersMap;


    public UserService(Map<String, User> userIdToUserMap,
                       Map<String, Tweet> tweetIdToTweetMap,
                       Map<String, List<String>> usersToTweetIdMap,
                       Map<String, List<String>> followersMap) {
        this.userIdToUserMap = userIdToUserMap;
        this.tweetIdToTweetMap = tweetIdToTweetMap;
        this.usersToTweetIdMap = usersToTweetIdMap;
        this.followersMap = followersMap;

        this.notificationService = new NotificationService();
        this.timelineService = new TimelineService();
        this.tweetSearchService = new  TweetSearchService();
    }

    // Method for a user to post a tweet
    public void postTweet(User user, Content content) {
        Tweet newTweet = new Tweet(content, user.getId());
        String tweetId = UUID.randomUUID().toString();

        tweetIdToTweetMap.put(tweetId, newTweet);

        System.out.println(user.getName() + " posted a new tweet: " + content);

        // Notify followers
        notificationService.notifyTweetFollowers(user, newTweet, this.getFollowers(user.getId()));
    }


    public void dislikeTweet(String userId, String TweetId){
        tweetIdToTweetMap.get(TweetId).dislikeTweet(userId);
    }


    public List<Tweet> getTopLikedTweets(String userId){


        // can keep a priority_Queeu for every user of certain amount
        // whenever we like or dislike
        List<Tweet> tweets = new ArrayList<>();

        for(String tweetId : usersToTweetIdMap.get(userId)){
            Tweet tweet = tweetIdToTweetMap.get(tweetId);
            tweets.add(tweet);
        }

        tweets.sort((t1, t2) -> Integer.compare(t1.getLikesCount(), t2.getLikesCount()));

        return tweets;

    }

    public void likeTweet(String userId, String TweetId){
        tweetIdToTweetMap.get(TweetId).likeTweet(userId);
    }

    // Method for a user to follow another user
    public void follow(String followerId, String followeeId)
    {
        User follower = userIdToUserMap.get(followerId);
        User followee = userIdToUserMap.get(followeeId);

        followersMap.get(followeeId).add(followerId);

        // Notify the user being followed
        notificationService.notifyFollowedUser(followee, follower);
    }

    // Method for a user to comment on another user's tweet
    public void comment(User commenter, User tweetOwner, Tweet tweet, String commentContent) {
        // Create a new comment
        Comment newComment = new Comment(commenter, commentContent);

        // Notify the tweet owner that someone commented on their tweet
        System.out.println(commenter.getName() + " commented on " + tweetOwner.getName() + "'s tweet: " + commentContent);

        // Notify the tweet owner about the comment
        notificationService.notifyTweetOwner(tweetOwner, commenter, tweet);

        // Optionally, we can store the comment in some database or list (not implemented here)
    }

    public List<String> getTimeline(List<String> followedPosts) {
        return timelineStrategy.createTimeline(followedPosts);
    }

    public void setTimelineStrategy(TimelineStrategy strategy) {
        this.timelineStrategy = strategy;
    }

    private List<User> getFollowers(String userId) {
        // Fetch the list of follower IDs
        List<String> followerIds = followersMap.getOrDefault(userId, new ArrayList<>());
        List<User> followers = new ArrayList<>();

        // Convert follower IDs to User objects
        for (String followerId : followerIds) {
            User user = userIdToUserMap.get(followerId);
            if (user != null) { // Check to avoid null for invalid user IDs
                followers.add(user);
            }
        }

        return followers;
    }

}