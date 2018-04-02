package com.interview.model;

public class Transactions {

    private Integer id;
    private String fromAccountId;
    private String toAccountId;
    private Double amountToTransfer;

    public Transactions(Integer id, String fromAccountId, String toAccountId, Double amountToTransfer) {
        super();
        this.id = id;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amountToTransfer = amountToTransfer;
    }

    public Transactions(String fromAccountId, String toAccountId, Double amountToTransfer) {
        super();
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amountToTransfer = amountToTransfer;
    }

    public Transactions() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Double getAmountToTransfer() {
        return amountToTransfer;
    }

    public void setAmountToTransfer(Double amountToTransfer) {
        this.amountToTransfer = amountToTransfer;
    }
}