package model;

import java.time.LocalDateTime;
import java.util.*;


public class Tweet {

    private String tweetId;

    private User user;

    private LocalDateTime tweetTime;

    private String content;

    private List<String> hashtags;

    private List<String> taggedUsers;

    private Map<String, CommentThread> commentThreads = new HashMap<String, CommentThread>();


    public void comment(String threadId, Comment c) {

        commentThreads.putIfAbsent(threadId, new CommentThread(threadId));
        commentThreads.get(threadId).addComments(c);

    }

    public boolean isBefore(Tweet otherTweet) {
        return this.tweetTime.isBefore(otherTweet.tweetTime) ? true : false;
    }

    public String describe() {
        StringBuilder br = new StringBuilder();
        br.append(user.userId() + Constants.LINE_BREAK);
        br.append("Tweeted At : " + tweetTime + Constants.LINE_BREAK);
        br.append(content + Constants.LINE_BREAK);
        br.append(hashtags + Constants.LINE_BREAK);
        br.append(taggedUsers + Constants.LINE_BREAK);
        br.append(commentThreads.values());
        return br.toString();
    }






}