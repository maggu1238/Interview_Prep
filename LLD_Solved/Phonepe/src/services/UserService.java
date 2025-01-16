package services;

import datastore.ContestantData;
import datastore.ProblemsData;
import model.Contestant;
import model.PlatformProblem;
import model.Problem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserService implements  IUserService{
    private ContestantData contestantData;
    private ProblemsData problemsData;

    public UserService(ContestantData contestantData, ProblemsData problemsData){
        this.contestantData = contestantData;
        this.problemsData = problemsData;
    }

    @Override
    public void registerUser(String name, String department) {
        // create factory for creating users
        String userId = UUID.randomUUID().toString();  // Generates a unique ID
        Contestant contestant = new Contestant(userId, name, department);
        contestantData.addUser(userId, contestant);
    }

    @Override
    public void solveProblem(String userId, String problemId, float time) {
        PlatformProblem problem = problemsData.getProblemById(problemId);
        Contestant contestant = contestantData.getUserById(userId);
        float score = problem.getScore();

        problemsData.addUserIdSolvedByProblemId(userId, problemId);

        float avgTime  = problem.getAvgTime();
        int solveCount = problem.getSolveCount();
        problem.setSolveCount(solveCount + 1);

        problem.setAvgTime(((avgTime * problem.getSolveCount() - 1) + time) / problem.getSolveCount());

        contestantData.addSolvedProblemByUserId(userId, problemId);
        contestant.UpdateScore(score);

    }

    @Override
    public List<Problem> fetchSolvedProblems(String userId) {
        List<String> problemIds = contestantData.getSolvedProblemByUserId(userId);
        List<Problem> problems = new ArrayList<>();

        for( String problemId : problemIds){
            problems.add(problemsData.getProblemById(problemId));
        }
        return problems;
    }
}
