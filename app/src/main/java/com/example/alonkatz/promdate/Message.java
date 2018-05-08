package com.example.alonkatz.promdate;

import java.util.Date;

public class Message {
    private String messageText;
    private String toID;
    private String fromID;
    private long messageTime;


    public Message(String messageText, String toID, String fromID){
        this.messageText = messageText;
        this.toID = toID;
        this.fromID = fromID;

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

    public String getFromID() {
        return fromID;
    }

    public long getMessageTime() {
        return messageTime;
    }
}
