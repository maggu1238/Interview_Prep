package model;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class User {
    private static final AtomicLong idCounter = new AtomicLong(0);
    private final long id;
    private final String name;
    private final Set<Long> following;

    public User(String name) {
        this.id = idCounter.incrementAndGet();
        this.name = name;
        this.following = new HashSet<>();
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public Set<Long> getFollowing() { return following; }

    public void follow(long userId) { following.add(userId); }
    public void unfollow(long userId) { following.remove(userId); }
}
