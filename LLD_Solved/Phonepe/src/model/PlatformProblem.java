package model;

import enums.Difficulty;

import java.util.List;

public class PlatformProblem extends Problem{
    private List<String> tags;
    private float avgTime;
    private int likesCount;
    private int solveCount;

    public PlatformProblem(String id, String title, String desc, List<String> tags, Difficulty difficulty, float score) {
        super(id, title, desc, score, difficulty);
        this.tags = tags;
        this.avgTime = 0;
        this.likesCount = 0;
        this.solveCount = 0;

    }

    public float getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(float avgTime) {
        this.avgTime = avgTime;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getSolveCount() {
        return solveCount;
    }

    public void setSolveCount(int solveCount) {
        this.solveCount = solveCount;
    }
}