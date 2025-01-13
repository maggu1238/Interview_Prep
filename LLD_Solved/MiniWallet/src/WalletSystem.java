import model.User;
import services.UserService;

import java.util.HashMap;
import java.util.Map;

public class WalletSystem {

    Map<String, User> registeredUsers;
    private UserService userService;

    public WalletSystem(){
        this.registeredUsers = new HashMap<>();

        this.userService = new UserService(registeredUsers);
    }

    public void registerUser(String userId){
        userService.createUser(userId);
    }

    public void removeUser(String userId){
        userService.removeUser(userId);
    }

    public void loadMoney(String userId, int amount){
        userService.loadMoney(userId, amount);
    }


            makeTxn(userId, userId, amount)
                gettxn(userId, filterType, sortType)

}
