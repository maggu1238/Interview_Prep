package strategies.Issue;

import model.User;

import java.util.*;

// First-Come-First-Served Strategy
class FirstComeFirstServedStrategy implements IssueStrategy {
    @Override
    public User selectUser(List<User> users) {
        return users.get(0); // First user in the list
    }
}
