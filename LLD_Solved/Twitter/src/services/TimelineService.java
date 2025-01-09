package services;

import model.User;
import strategies.TimelineStrategy;

import java.util.*;
import java.util.List;

public class TimelineService {
    private TimelineStrategy timelineStrategy;

    public TimelineService(TimelineStrategy timelineStrategy) {
        this.timelineStrategy = timelineStrategy;
    }

    public List<Tweet> getTimeline(User user) {
        List<Tweet> followedPosts = new ArrayList<>();
        for (User followedUser : user.getFollowing()) {
            followedPosts.addAll(followedUser.getPosts());
        }
        return timelineStrategy.createTimeline(followedPosts);
    }

    public void setTimelineStrategy(TimelineStrategy strategy) {
        this.timelineStrategy = strategy;
    }
}
