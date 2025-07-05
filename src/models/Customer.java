package models;

public class Customer {
    private String name;
    private double balance;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public boolean hasSufficientBalance(double amount) {
        return balance >= amount;
    }

    public void deductBalance(double amount) {
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }
}