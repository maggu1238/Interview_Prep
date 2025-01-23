package services.UserService;

class UserService implements IUserService {
    private Map<Integer, User> users = new HashMap<>();
    private int userIdCounter = 1;

    @Override
    public void addUser(String name, String email, String role) {
        User user = new User(userIdCounter++, name, email, role);
        users.put(user.id, user);
        System.out.println("User added: " + name);
    }

    @Override
    public void removeUser(int userId) {
        if (users.containsKey(userId)) {
            users.remove(userId);
            System.out.println("User removed: " + userId);
        } else {
            System.out.println("User not found.");
        }
    }

    @Override
    public User getUserById(int userId) {
        return users.get(userId);
    }
}
