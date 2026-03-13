package com.example.Server4.STOMPWebSocket;

import java.time.LocalDateTime;

public class OutputMessage extends Message{
    public OutputMessage(String author, String message, LocalDateTime timeStamp){
        super(author, message, timeStamp);
    }
}
