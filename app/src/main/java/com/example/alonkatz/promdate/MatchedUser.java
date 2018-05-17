package com.example.alonkatz.promdate;

public class MatchedUser {

    private String name;
    private String userID;

    public MatchedUser(String name, String userID){
        this.name = name;
        this.userID = userID;
    }

    public MatchedUser(){}
    public String getName(){return name;}
    public String getUserID(){return userID;}
}
