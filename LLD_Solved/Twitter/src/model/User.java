package model;

import strategies.TimelineStrategy;
import java.util.*;

public class User{
    private String name;
    private String id;
    private List<String> posts; // Posts by the user
    private TimelineStrategy timelineStrategy; // Strategy for generating timeline
    private List<User> following; // Users this user follows
    private List<Follower> followers; // Followers observing this user

    public User(String id, String name, TimelineStrategy timelineStrategy) {
        this.id = id;
        this.name = name;
        this.posts = new ArrayList<>();
        this.timelineStrategy = timelineStrategy;
        this.following = new ArrayList<>();
        this.followers = new ArrayList<>();
    }

    // Add a follower (Observer)
    public void addFollower(Follower follower) {
        followers.add(follower);
        // Notify the user being followed
        if (follower instanceof User) {
            this.notifyFollow(((User) follower).getName());
        }
    }

    // Notify all followers of a new tweet
    private void notifyFollowers(String tweet) {
        for (Follower follower : followers) {
            follower.notifyTweet(tweet, name);
        }
    }

    // Post a tweet
    public void postTweet(String tweet) {
        posts.add(tweet);
        System.out.println(name + " tweeted: " + tweet);
        notifyFollowers(tweet);
    }

    // Follow another user (Observer registers with Observable)
    public void follow(User user) {
        user.addFollower(this);
        following.add(user);
        System.out.println(name + " is now following " + user.getName() + ".");
    }

    // Observer's update method for tweets
    @Override
    public void notifyTweet(String tweet, String userName) {
        System.out.println("[" + name + "] " + userName + " tweeted: " + tweet);
    }

    // Observer's notify method for follow actions
    @Override
    public void notifyFollow(String followerName) {
        System.out.println("[" + name + "] You are now followed by " + followerName + "!");
    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getPosts() {
        return posts;
    }

    public List<String> getTimeline(List<String> followedPosts) {
        return timelineStrategy.createTimeline(followedPosts);
    }

    public void setTimelineStrategy(TimelineStrategy strategy) {
        this.timelineStrategy = strategy;
    }
}