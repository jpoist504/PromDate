package com.example.alonkatz.promdate;

/**
 *
 * @author Justin
 * Represents a user that is matched with the user in the context of the firebase database
 */
public class MatchedUser {

    private String name;
    private String userID;

    public MatchedUser(String name, String userID){
        this.name = name;
        this.userID = userID;
    }

    @Override
    public String toString() {
        return userID;
    }

    public MatchedUser(){}
    public String getName(){return name;}
    public String getUserID(){return userID;}
}
