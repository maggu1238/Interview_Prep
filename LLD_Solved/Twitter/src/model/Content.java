package model;

import java.util.List;

public class Content {

    private List<String> taggedUsers;
    private List<String> hashtags;
    private String desc;

    public Content(String desc, List<String> taggedUsers, List<String> hashtags){
        this.desc = desc;
        this.taggedUsers = taggedUsers;
        this.hashtags = hashtags;
    }


    public List<String> getTaggedUsers() {
        return taggedUsers;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public String getDesc() {
        return desc;
    }
}
