package com.interview.model;

public class Account {

    private String id;
    private Double balance;

    public Account(){
        super();
    }

    public Account(String id, Double balance) {
        super();
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
