package services;

import model.*;

import java.util.*;

public class TweetService {
    private final Map<Long, Tweet> tweets;
    private final LikeService likeService;

    public TweetService(LikeService likeService) {
        this.tweets = new HashMap<>();
        this.likeService = likeService;
    }

    public Tweet postTweet(User user, String content) {
        Tweet tweet = new Tweet(user.getId(), content);
        tweets.put(tweet.getId(), tweet);
        return tweet;
    }

    public void likeTweet(long tweetId, long userId) {
        if (!tweets.containsKey(tweetId)) {
            throw new IllegalArgumentException("Tweet not found!");
        }
        likeService.likeTweet(tweetId, userId); // Only TweetService can access it
    }

    public void dislikeTweet(long tweetId, long userId) {
        if (!tweets.containsKey(tweetId)) {
            throw new IllegalArgumentException("Tweet not found!");
        }
        likeService.dislikeTweet(tweetId, userId); // Only TweetService can access it
    }

    public Tweet getTweet(long tweetId) {
        return tweets.get(tweetId);
    }
}

