package com.example.alonkatz.promdate;

import java.util.Date;

/**
 * @author Justin
 * Represents a message that has the time it was sent, the user it is going to, the sending user, and the message itself
 */
public class Message {
    private String messageText;
    private String toID;
    private String fromID;



    public Message(String messageText, String toID, String fromID){
        this.messageText = messageText;
        this.toID = toID;
        this.fromID = fromID;

    }

    public Message() {
    }

    public String getMessageText() {
        return messageText;
    }

    public String getToID() {
        return toID;
    }

    public String getFromID(){return fromID;}

    public String toString(){
        return messageText;
    }


}
