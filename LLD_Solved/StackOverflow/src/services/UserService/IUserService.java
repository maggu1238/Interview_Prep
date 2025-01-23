package services.UserService;

public interface IUserService {
    void addUser(String name, String email, String role);
    void removeUser(int userId);
    User getUserById(int userId);
}