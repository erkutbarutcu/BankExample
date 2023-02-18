package org.example;

public class User {
    public String name;
    public String TC;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTC() {
        return TC;
    }

    public void setTC(String TC) {
        this.TC = TC;
    }

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int accountID) {
        AccountID = accountID;
    }

    public double getAccountValue() {
        return AccountValue;
    }

    public void setAccountValue(double accountValue) {
        AccountValue = accountValue;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public User(String name, String TC, int accountID, double accountValue, String password) {
        this.name = name;
        this.TC = TC;
        AccountID = accountID;
        AccountValue = accountValue;
        Password = password;
    }

    public int AccountID;
    public double AccountValue;
    public String Password;


}
