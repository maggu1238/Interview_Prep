package strategies;

import java.util.List;

public interface TimelineStrategy {
    List<String> createTimeline(List<String> followedPosts);;
}
