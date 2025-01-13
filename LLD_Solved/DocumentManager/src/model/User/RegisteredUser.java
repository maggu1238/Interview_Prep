package model.User;

public class RegisteredUser extends Person {
    private final String userId;
    private final Account account;

    public RegisteredUser(String userId, Account account, String firstName, String lastName, String phoneNumber, Address address) {
        super(firstName, lastName, phoneNumber, address);
        this.userId = userId;
        this.account = account;
    }

    public String getUserId() {
        return this.userId;
    }
}