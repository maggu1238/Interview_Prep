import services.*;

class TwitterSystem {
    private final UserService userService;
    private final TweetService tweetService;
    private final LikeService likeService;
//    private final NewsFeedService newsFeedService;

    public TwitterSystem() {
        this.userService = new UserService();
        this.likeService = new LikeService();
        this.tweetService = new TweetService(likeService);
//        this.newsFeedService = new NewsFeedService(tweetService, userService);
    }

    public UserService getUserService() { return userService; }
    public TweetService getTweetService() { return tweetService; }
    public LikeService getLikeService() { return likeService; }
//    public NewsFeedService getNewsFeedService() { return newsFeedService; }
}