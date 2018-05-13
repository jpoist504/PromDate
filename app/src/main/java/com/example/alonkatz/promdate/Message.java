package com.example.alonkatz.promdate;

import java.util.Date;

/**
 * @author Justin
 * Represents a message that has the time it was sent, the user it is going to, the sending user, and the message itself
 */
public class Message {
    private String messageText;
    private String toID;
    private long messageTime;


    public Message(String messageText, String toID){
        this.messageText = messageText;
        this.toID = toID;


        messageTime = new Date().getTime();
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


    public long getMessageTime() {
        return messageTime;
    }
}
