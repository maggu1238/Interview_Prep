package services;

import java.util.*;

public class LikeService {
    private final Map<Long, Set<Long>> tweetLikes; // Tweet ID -> Set of User IDs

    public LikeService() {
        this.tweetLikes = new HashMap<>();
    }

    // Package-private (no `public` keyword) so only TweetService can modify likes
    void likeTweet(long tweetId, long userId) {
        tweetLikes.computeIfAbsent(tweetId, k -> new HashSet<>()).add(userId);
    }

    void dislikeTweet(long tweetId, long userId) {
        tweetLikes.getOrDefault(tweetId, new HashSet<>()).remove(userId);
    }

    // Public method to get like count (safe to expose)
    public int getLikeCount(long tweetId) {
        return tweetLikes.getOrDefault(tweetId, Collections.emptySet()).size();
    }
}
