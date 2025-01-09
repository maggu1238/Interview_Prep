package model;
import java.util.*;

public class CommentThread {

    private String threadId;
    ArrayList<Comment> comments;

    public CommentThread(String threadId) {
        this.threadId = threadId;
    }

    public void addComments(Comment comment){
        comments.add(comment);
    }

    public List<Comment> getComments(){
        return comments;
    }
}