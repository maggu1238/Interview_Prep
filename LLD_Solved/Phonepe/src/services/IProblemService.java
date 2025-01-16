package services;

import enums.Difficulty;
import enums.FilterType;
import enums.SortType;
import model.Problem;

import java.util.List;

public interface IProblemService {

    void addProblem(String title, String desc, List<String> tags, Difficulty difficulty, float score);
    List<Problem> fetchProblems(Difficulty filterType, SortType sort);
}
