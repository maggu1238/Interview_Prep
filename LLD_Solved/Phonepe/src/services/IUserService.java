package services;

import model.Problem;

import java.util.List;

public interface IUserService {
    void registerUser(String name, String department);
    void solveProblem(String userId, String problemId, float time);
    List<Problem> fetchSolvedProblems(String userId);
}
