package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private int balance;
    private List<Transaction> transactions;


    public User(String userId) {
        this.userId = userId;
        this.balance = 0;
        transactions = new ArrayList<>();
    }

    public int getBalance() {
        return balance;
    }

    public void creditBalance(int amount) {
        this.balance = this.balance + amount;
    }

    public void debitBalance(int amount){
        this.balance = this.balance - amount;
    }

}
