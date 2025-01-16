package datastore;

import Exceptions.DuplicateKeyException;
import Exceptions.NoUserFoundException;
import model.Contestant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ContestantData {
    private Map<String, Contestant> userIdToUser;
    private Map<String, List<String>> userIdToSolvedProblemIdMap;

    public ContestantData() {
        this.userIdToUser = new HashMap<>();
        this.userIdToSolvedProblemIdMap = new HashMap<>();
    }

    public Contestant getUserById(String userId){
        if(Objects.isNull(userId)) throw new NoUserFoundException("No user found with Id: " + userId);

        Contestant contestant = userIdToUser.get(userId);
        return contestant;
    }

    public List<String> getSolvedProblemByUserId(String userId){
        if(Objects.isNull(userId)) throw new NoUserFoundException("No user found with Id: " + userId);

        List<String> solvedProblems = userIdToSolvedProblemIdMap.get(userId);
        return solvedProblems;
    }

    public void addSolvedProblemByUserId(String userId, String problemId) {
        if (Objects.isNull(userId) || Objects.isNull(problemId)) {
            throw new IllegalArgumentException("User ID and Problem Id must not be null.");
        }

        List<String> problems = userIdToSolvedProblemIdMap.get(userId);
        problems.add(problemId);
        userIdToSolvedProblemIdMap.put(userId, problems);
    }

    // Add a user to the map
    public void addUser(String userId, Contestant contestant) {
        if (Objects.isNull(userId) || Objects.isNull(contestant)) {
            throw new IllegalArgumentException("User ID and Contestant must not be null.");
        }

        if (userIdToUser.containsKey(userId)) {
            throw new DuplicateKeyException("User with ID " + userId + " already exists.");
        }

        userIdToUser.put(userId, contestant);
    }
}
