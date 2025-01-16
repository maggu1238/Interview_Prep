package services;

import datastore.ProblemsData;
import enums.Difficulty;
import enums.FilterType;
import enums.SortType;
import model.PlatformProblem;
import model.Problem;
import strategies.FilterStrategyByDifficulty;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ProblemService implements IProblemService{
    private ProblemsData problemsData;

    public ProblemService(ProblemsData problemsData){
        this.problemsData = problemsData;
    }

    @Override
    public void addProblem(String title, String desc, List<String> tags, Difficulty difficulty, float score) {

        String problemId = UUID.randomUUID().toString();  // Generates a unique ID
        PlatformProblem platformProblem = new PlatformProblem(problemId, title, desc, tags,  difficulty,  score);
        problemsData.addProblem(problemId, platformProblem);
    }

    @Override
    public List<Problem> fetchProblems(Difficulty filterType, SortType sort) {

        FilterStrategyByDifficulty  filterStrategyByDifficulty = new FilterStrategyByDifficulty();
        List<Problem> filteredProblems = filterStrategyByDifficulty.filter(problemsData.getProblems(), filterType);



        switch(sort){
            case ScoreAcsending:
                Collections.sort(filteredProblems, new Comparator<Problem>() {
                    @Override
                    public int compare(Problem p1, Problem p2) {
                        return Float.compare(p1.getScore(), p2.getScore());
                      }
                });
            case ScoreDescending:
                Collections.sort(filteredProblems, new Comparator<Problem>() {
                    @Override
                    public int compare(Problem p1, Problem p2) {
                            return Float.compare(p2.getScore(), p1.getScore());
                    }
                });
        }

        return filteredProblems;
    }
}
