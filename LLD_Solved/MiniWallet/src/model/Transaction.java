package model;

import enums.TransactionType;

public class Transaction {
    private String creditorUserId;
    private String debitorUserId;
    private int amount;
    private TransactionType transactionType;

    public Transaction(String creditorUserId, String debitorUserId, int amount) {
        this.creditorUserId = creditorUserId;
        this.debitorUserId = debitorUserId;
        this.amount = amount;
        this.transactionType = TransactionType.Initiate;
    }

}
