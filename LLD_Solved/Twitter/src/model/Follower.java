package model;

public interface Follower {
    void update(String tweet, String userName); // Notify about tweets

    void notifyFollow(String followerName); // Notify when someone follows
}