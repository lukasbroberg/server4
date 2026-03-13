package com.example.Server4.STOMPWebSocket;

import java.time.LocalDateTime;

public class Message {

    private String author;
    private LocalDateTime timeStamp;
    private String message;

    public Message(String message, String author, LocalDateTime timeStamp){
        this.message=message;
        this.author=author;
        this.timeStamp=timeStamp;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){this.message=message;}

    public String getAuthor(){
        return this.author;
    }

    public void setAuthor(String author){this.author=author;}

    public LocalDateTime getTimeStamp(){
        return this.timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp){this.timeStamp=timeStamp;}

}
