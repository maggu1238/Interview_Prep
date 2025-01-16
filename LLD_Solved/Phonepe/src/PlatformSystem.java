import datastore.ContestantData;
import datastore.ProblemsData;
import enums.Difficulty;
import enums.SortType;
import model.Problem;
import services.ProblemService;
import services.UserService;

import java.util.List;

public class PlatformSystem {

    private static PlatformSystem platformSystem;
    private UserService userService;
    private ProblemService problemService;
    private ContestantData contestantData;
    private ProblemsData problemsData;

    private PlatformSystem(){
        this.problemsData = new ProblemsData();
        this.contestantData = new ContestantData();

        this.userService = new UserService(contestantData, problemsData);
        this.problemService = new ProblemService(problemsData);
    }

    public static PlatformSystem createPlatformSystem(){
        if(platformSystem == null){
            return new PlatformSystem();
        }
        return platformSystem;
    }


    public void addProblem(String title, String desc, List<String> tags, Difficulty difficulty, float score){
        this.problemService.addProblem(title,desc,tags,difficulty,score);
    }

    public List<Problem> fetchProblems(Difficulty filterType, SortType sort){
        return this.problemService.fetchProblems(filterType, sort);
    }

    public void registerUser(String name, String department){
        this.userService.registerUser(name, department);
    }

    public void solveProblem(String userId, String problemId, float time){
        this.userService.solveProblem(userId, problemId, time);
    }

    public List<Problem> fetchSolvedProblems(String userId)
    {
        return this.userService.fetchSolvedProblems(userId);
    }

}
