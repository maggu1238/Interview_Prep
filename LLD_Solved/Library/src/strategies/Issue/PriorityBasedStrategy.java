package strategies.Issue;

import model.User;

import java.util.List;

class PriorityBasedStrategy implements IssueStrategy {
    @Override
    public User selectUser(List<User> users) {
        // Assuming higher priority is determined by user ID (e.g., lexicographically smaller ID has higher priority)
        return users.stream().min((u1, u2) -> u1.getUserId().compareTo(u2.getUserId())).orElse(null);
    }
}