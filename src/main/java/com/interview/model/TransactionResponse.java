package com.interview.model;

public class TransactionResponse {

    private Integer transactionId;
    private Double balance;

    public TransactionResponse(Integer transactionId, Double balance) {
        super();
        this.transactionId = transactionId;
        this.balance = balance;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        balance = balance;
    }
}