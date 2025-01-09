import services.*;
import model.*;
import java.util.*;

public class TwitterSystem {

    private List<User> users;
    private List<Tweet> allTweets;
    private UserService userService;
    private TweetSearchService tweetSearchService;

    private static TwitterSystem instance;

    private TwitterSystem() {
        this.users = new ArrayList<>();
        this.allTweets = new ArrayList<>();
        initializeServices();
    }

    public static TwitterSystem getInstance() {
        if (instance == null) {
            instance = new TwitterSystem();
        }
        return instance;
    }

    private void initializeServices() {
        this.userService = new UserService(this);
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