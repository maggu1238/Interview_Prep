package services;

import model.User;
import java.util.*;

public class UserService {
    private Map<String, User> registerdUsers;

    public UserService(Map<String, User> registerdUsers){
        this.registerdUsers = registerdUsers;
    }


    // check how to add try catch here
    public void createUser(String userId){
        if(userId.isEmpty()){
            System.out.println("User with userId " + userId + " has been registered");
        }
        if(!registerdUsers.containsKey(userId)) {
            registerdUsers.put(userId, new User(userId));
            System.out.println("User with userId " + userId + " has been registered");
        }
    }

    public void removeUser(String userId){
        if(registerdUsers.containsKey(userId)){
            registerdUsers.remove(userId);
            System.out.println("UserId removed");
        }
        else{
            System.out.println("UserId is not present");
        }
    }

    public void loadMoney(String userId, int amount){
        User user = registerdUsers.get(userId);
        user.creditBalance(amount);
        return;
    }

    public int getBalance(String userId){
        return registerdUsers.get(userId).getBalance();
    }
}
