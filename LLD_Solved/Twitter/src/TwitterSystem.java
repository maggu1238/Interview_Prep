import services.*;
import model.*;
import strategies.HashtagSearchStrategy;

import java.util.*;

public class TwitterSystem {

    private Map<String, User> userIdToUserMap;
    private Map<String, Tweet> tweetIdToTweetMap;
    private Map<String, List<String>> usersToTweetIdMap;
    private Map<String, List<String>> followersMap;

    private UserService userService;
    private TweetSearchService tweetSearchService;

    private static TwitterSystem instance;

    private TwitterSystem() {

        this.userIdToUserMap = new HashMap<>();
        this.tweetIdToTweetMap = new HashMap<>();
        this.usersToTweetIdMap = new HashMap<>();
        this.followersMap = new HashMap<>();

        initializeServices(userIdToUserMap, tweetIdToTweetMap, usersToTweetIdMap, followersMap);
    }

    public static TwitterSystem getInstance() {
        if (instance == null) {
            instance = new TwitterSystem();
        }
        return instance;
    }

    private void initializeServices(Map<String, User> userIdToUserMap,
                                    Map<String, Tweet> tweetIdToTweetMap,
                                    Map<String, List<String>> usersToTweetIdMap,
                                    Map<String, List<String>> followersMap) {
        this.userService = new UserService(userIdToUserMap, tweetIdToTweetMap, usersToTweetIdMap, followersMap);
        this.tweetSearchService = new TweetSearchService();
        this.tweetSearchService.setSearchStrategy(new HashtagSearchStrategy()); // Set default strategy
    }

    public void addUser(String userName) {
        User newUser = new User(userName);
        System.out.println(userName + " has been added to the system.");
    }

    public TweetSearchService getTweetSearchService() {
        return tweetSearchService;
    }

    public UserService getUserService() {
        return userService;
    }
}