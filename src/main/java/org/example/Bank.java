package org.example;

public class Bank {
    private static final double FAILURE_RATE = 0.1; // Simulate a 10% failure rate for transactions

    public boolean processTransaction(double amount) {
        double transactionOutcome = Math.random();
        return transactionOutcome >= FAILURE_RATE;
    }
}
