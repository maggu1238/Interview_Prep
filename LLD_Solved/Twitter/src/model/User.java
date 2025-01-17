package model;

import strategies.TimelineStrategy;
import java.util.*;

public class User implements observer{
    private String name;
    private String id;
    private List<String> posts; // Posts by the user
    private TimelineStrategy timelineStrategy; // Strategy for generating timeline
    private List<User> following; // Users this user follows
    private List<User> followers; // Followers observing this user

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.posts = new ArrayList<>();
        this.following = new ArrayList<>();
        this.followers = new ArrayList<>();
    }

    public String getId(){
        return id;
    }

    public String getFollowers(){
        return id;
    }

    public String getFollowing(){
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getPosts() {
        return posts;
    }

    @Override
    public void postUpdate(User user) {
        System.out.println("post made by user");
    }

    @Override
    public void followUpdate(User user) {
        System.out.println("suer followed you");
    }
}