package models;

public class User {

    private final int userId;
    private final int name;

    public User(int userId, int name) {
        this.userId = userId;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public int getName() {
        return name;
    }
}
