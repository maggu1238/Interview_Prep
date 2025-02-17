//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
// Online Java Compiler
// Use this editor to write, compile and run your Java code online
import java.security.Timestamp;
import java.util.*;

class User{
    String userId;

    public User(String userId){
        this.userId = userId;
    }
}

class Tweet{
    private String TweetId;
    private String content;
    private long timeStamp;

    public Tweet(String tweetId, String content, long timeStamp){
        this.TweetId = tweetId;
        this.content = content;
        this.timeStamp = timeStamp;
    }

    public String getContent(){
        return  content;
    }

    public long getTimeStamp(){
        return timeStamp;
    }

    public String getTweetId() {
        return TweetId;
    }
}

class TwitterSystem{
    private Map<String, List<String>> userTweets;
    private Map<String, Integer> tweetLikeCount;
    private Map<String, Tweet> tweetMap;
    private Map<String, List<String>> friendList;

    public TwitterSystem(){
        userTweets = new HashMap<>();
        tweetLikeCount = new HashMap<>();
        tweetMap = new HashMap<>();
        friendList = new HashMap<>();
    }

    void postTweet(String userId, Tweet tweet){
        userTweets.putIfAbsent(userId, new ArrayList<>());
        userTweets.get(userId).add(tweet.getTweetId());

        tweetLikeCount.putIfAbsent(tweet.getTweetId(),0);
        tweetMap.putIfAbsent(tweet.getTweetId(),tweet);
    }

    List<Tweet> getTopKTweets(String userId, int k){
        List<String> tweets = new ArrayList<>();
        tweets = userTweets.get(userId);

        // minHeap
        PriorityQueue<TweetWithLike> pq = new PriorityQueue<>(new LikeComparator());

        for( String tweetId : userTweets.get(userId)){
            TweetWithLike tweetWithLike = new TweetWithLike(tweetId, tweetLikeCount.get(tweetId));

            pq.offer(tweetWithLike);
            if(pq.size() > k){
                pq.poll();
            }
        }
        List<Tweet> result = new ArrayList<>();

        while(!pq.isEmpty()){
            result.add(tweetMap.get(pq.poll().tweetId));
        }
        return result;
    }

    private class LikeComparator implements Comparator<TweetWithLike>{

        @Override
        public int compare(TweetWithLike o1, TweetWithLike o2) {
            return Integer.compare(o1.likeCount, o2.likeCount);
        }
    }

    private class TweetWithLike{
        String tweetId;
        int likeCount;

        TweetWithLike(String tweetId, int likeCount){
            this.likeCount = likeCount;
            this.tweetId = tweetId;
        }
    }

    void likeTweet(String userId, String tweetId){
        int likes = tweetLikeCount.getOrDefault(tweetId, 0);
        likes++;
        tweetLikeCount.put(tweetId, likes);
    }

    void disLikeTweet(String userId, String tweetId){
        int likes = tweetLikeCount.getOrDefault(tweetId, 0);
        likes++;
        tweetLikeCount.put(tweetId, likes);
    }

    void followUser(String follower, String followee){
        friendList.get(follower).add(followee);
    }

    private class TimeStampComparator implements Comparator<Tweet>{

        @Override
        public int compare(Tweet o1, Tweet o2) {
            return Long.compare(o1.getTimeStamp(), o2.getTimeStamp());
        }
    }

    public List<Tweet> generateTimeLine(String userId){
        List<Tweet> timeline = new ArrayList<>();
        List<String> friends = friendList.get(userId);
        friends.add(userId);

        for(String friend : friendList.get(userId)){
            for(String tweetId : userTweets.get(friend)){
                timeline.add(tweetMap.get(tweetId));
            }
        }

        timeline.sort(new TimeStampComparator());
        return timeline;
    }
}

public class Main {
    public static void main(String[] args) {
        List<String> carIds = Arrays.asList("roomA", "roomB", "roomC");
        TwitterSystem twitterSystem =  new TwitterSystem();

//        System.out.println(reservationSystem.reserveCar(9, 10));
//        System.out.println(reservationSystem.reserveCar(9, 10));
//        System.out.println(reservationSystem.reserveCar(9, 10));
//        System.out.println(reservationSystem.reserveCar(9, 10));
//        System.out.println(reservationSystem.reserveCar(9, 10)); // Should succeed
    }
}