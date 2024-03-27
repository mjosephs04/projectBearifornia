package com.example.springboot;

public class LoggingIn {
    // Data Members
    private String Username = "";
    private String Password = "";


    // Constructor
    public LoggingIn(String Username, String Password) {
        this.Username = Username;
        this.Password = Password;
    }


    // Confirm Account Existence
    public Guest confirmLogin() {
        //REFERENCE DATABASE AND RETREIVE GUEST
        //IF GUEST NOT FOUND RETURN NULL
        //NOTE whoever Uses this needs to account for
        //NULL RETURNED GUEST and consider it as no account found
        return null;

    }
}
