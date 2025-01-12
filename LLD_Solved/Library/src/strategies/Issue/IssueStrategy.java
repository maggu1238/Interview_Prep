package strategies.Issue;

import model.User;

import java.util.List;

public interface IssueStrategy {
    User selectUser(List<User> users);
}

