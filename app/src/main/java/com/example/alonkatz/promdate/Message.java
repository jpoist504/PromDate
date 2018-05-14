package com.example.alonkatz.promdate;

import java.util.Date;

/**
 * @author Justin
 * Represents a message that has the time it was sent, the user it is going to, the sending user, and the message itself
 */
public class Message {
    private String messageText;
    private String toID;



    public Message(String messageText, String toID){
        this.messageText = messageText;
        this.toID = toID;

    }

    public Message() {
    }

    public String getMessageText() {
        return messageText;
    }

    public String getToID() {
        return toID;
    }

    public String toString(){
        return messageText;
    }


}
