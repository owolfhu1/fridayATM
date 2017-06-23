package com.orion;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int accountNumber;
    private int balance;
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getAccountNumber() {return accountNumber;}
    public void setAccountNumber(int accountNumber) {this.accountNumber = accountNumber;}
    public int getBalance() {return balance;}
    public void setBalance(int balance) {this.balance = balance;}
}