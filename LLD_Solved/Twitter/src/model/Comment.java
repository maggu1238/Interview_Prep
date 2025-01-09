package model;

public class Comment {

    private User tweeter;
    private String desc;

    // Constructor
    public Comment(User tweeter, String desc) {
        this.tweeter = tweeter;
        this.desc = desc;
    }

    // Method to show the comment
    public String showComment() {
        return tweeter.getId() + " : " + desc;
    }
}