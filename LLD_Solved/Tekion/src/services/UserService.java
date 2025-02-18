package services;

import model.User;

import java.util.*;

public class UserService {
    private final Map<Long, User> users;

    public UserService() {
        this.users = new HashMap<>();
    }

    public User registerUser(String name) {
        User user = new User(name);
        users.put(user.getId(), user);
        return user;
    }

    public User getUser(long userId) {
        return users.get(userId);
    }
}
