package datastore;


import Exceptions.DuplicateKeyException;
import Exceptions.NoProblemFoundException;
import model.PlatformProblem;
import model.Problem;

import java.util.*;

public class ProblemsData {
    private Map<String, PlatformProblem> problemIdtoProblem;
    private Map<String, List<String>> problemIdToUserIdsSolved;
    private List<Problem> problems;

    public ProblemsData() {
        this.problemIdtoProblem = new HashMap<>();
        this.problemIdToUserIdsSolved = new HashMap<>();
        this.problems = new ArrayList<>();
    }

    public PlatformProblem getProblemById(String problemId){
        if(Objects.isNull(problemId)) throw new NoProblemFoundException("No problem found with Id: " + problemId);

        PlatformProblem problem = problemIdtoProblem.get(problemId);
        return problem;
    }

    public List<String> getUserIdsSolvedByProblemId(String problemId){
        if(Objects.isNull(problemId)) throw new NoProblemFoundException("No problem found with Id: " + problemId);

        List<String> solvedUserIds = problemIdToUserIdsSolved.get(problemId);
        return solvedUserIds;
    }

    public void addUserIdSolvedByProblemId(String userId, String problemId) {
        if (Objects.isNull(userId) || Objects.isNull(problemId)) {
            throw new IllegalArgumentException("User ID and Problem Id must not be null.");
        }

        List<String> userIds = problemIdToUserIdsSolved.get(problemId);
        userIds.add(userId);
        problemIdToUserIdsSolved.put(problemId, userIds);

    }


    // Add a user to the map
    public void addProblem(String problemId, PlatformProblem problem) {
        if (Objects.isNull(problemId) || Objects.isNull(problem)) {
            throw new IllegalArgumentException("Problem ID and Problem must not be null.");
        }

        if (problemIdtoProblem.containsKey(problemId)) {
            throw new DuplicateKeyException("Probelm with ID " + problemId + " already exists.");
        }

        problemIdtoProblem.put(problemId, problem);
        problems.add(problem);
    }

    public List<Problem> getProblems(){
        return problems;
    }
}
