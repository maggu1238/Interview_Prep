package model;

import java.time.LocalDateTime;
import java.util.*;


public class Tweet {

    private String tweetId;

    private String user;

    private LocalDateTime tweetTime;

    private Content content;

    private List<String> likedUsers;

    private Map<String, CommentThread> commentThreads;

    private int likesCount;

    public Tweet(Content content, String user){
        commentThreads = new HashMap<>();
        this.likedUsers = new ArrayList<>();
        this.content = content;
        this.user = user;
        this.likesCount = 0;
    }

    public void likeTweet(String userId){
        likedUsers.add(userId);
        likesCount++;
        return;
    }

    public void dislikeTweet(String userId){
        likedUsers.removeIf(str -> str.equals(userId));
        likesCount--;
    }

    public void comment(String threadId, Comment c) {
        commentThreads.putIfAbsent(threadId, new CommentThread(threadId));
        commentThreads.get(threadId).addComments(c);
    }

    public Content getContent(){

        return content;
    }

    public boolean isBefore(Tweet otherTweet) {

        return this.tweetTime.isBefore(otherTweet.tweetTime) ? true : false;
    }

    public int getLikesCount() {
        return likesCount;
    }

//    public String describe() {
//        StringBuilder br = new StringBuilder();
//        br.append(user.userId() + Constants.LINE_BREAK);
//        br.append("Tweeted At : " + tweetTime + Constants.LINE_BREAK);
//        br.append(content + Constants.LINE_BREAK);
//        br.append(hashtags + Constants.LINE_BREAK);
//        br.append(taggedUsers + Constants.LINE_BREAK);
//        br.append(commentThreads.values());
//        return br.toString();
//    }






}