package model;

import java.util.Map;

public class Contestant extends Participant {
    private float totalScore;

    public Contestant(String id, String name, String department) {
        super(id, name, department);
        this.totalScore = 0;
    }

    public void UpdateScore(float marks){
        this.totalScore = totalScore + marks;
    }

    public float getTotalScore() {
        return totalScore;
    }
}