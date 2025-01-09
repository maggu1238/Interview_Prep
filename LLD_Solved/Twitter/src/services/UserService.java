package services;

package services;

public class UserService {
    private TweetService tweetService;
    private FollowService followService;
    private CommentService commentService;

    public UserService() {

    }

    // Method for a user to post a tweet
    public void postTweet(User user, String content) {
        Tweet newTweet = new Tweet(content, user.getId());
        tweetRepository.addTweet(newTweet);

        System.out.println(user.getName() + " posted a new tweet: " + content);

        // Notify followers
        notificationService.notifyFollowers(user, user.getName() + " tweeted: " + content);
    }

    // Method for a user to follow another user
    public void follow(User follower, User followee) {
        // Add the follower to the followee's followers list (observer pattern)
        followee.addObserver(follower);

        // Add the followee to the follower's following list
        follower.addFollowing(followee);

        // Notify the followee that they have a new follower
        System.out.println(follower.getName() + " is now following " + followee.getName() + ".");

        // Notify the user being followed
        notificationService.notifyFollowedUser(followee, follower);
    }

    // Method for a user to comment on another user's tweet
    public void comment(User commenter, User tweetOwner, Tweet tweet, String commentContent) {
        // Create a new comment
        Comment newComment = new Comment(commenter, commentContent);

        // Notify the tweet owner that someone commented on their tweet
        System.out.println(commenter.getName() + " commented on " + tweetOwner.getName() + "'s tweet: " + commentContent);

        // Notify the tweet owner about the comment
        notificationService.notifyTweetOwner(tweetOwner, commenter, tweet);

        // Optionally, we can store the comment in some database or list (not implemented here)
    }




}