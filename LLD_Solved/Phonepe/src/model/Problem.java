package model;

import enums.Difficulty;

public class Problem {

    private final String id;
    private final String title;
    private final String desc;
    private final float score;
    private final Difficulty difficulty;


    public Problem(String id, String title, String desc, float score, Difficulty difficulty) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.score = score;
        this.difficulty = difficulty;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public float getScore() {
        return score;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
