package com.example.e;

public class Transaction {
    private String title;
    private double amount;
    private boolean income;

    public Transaction(String title, double amount, boolean income) {
        this.title = title;
        this.amount = amount;
        this.income = income;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isIncome() {
        return income;
    }
}
