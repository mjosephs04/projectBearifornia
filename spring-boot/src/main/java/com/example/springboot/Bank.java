package com.example.springboot;

public class Bank {
    private static final double FAILURE_RATE = 0.05;

    public boolean processTransaction(double amount) {
        double transactionOutcome = Math.random();
        return transactionOutcome >= FAILURE_RATE;
    }
}
