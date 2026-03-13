package com.example.Server4.STOMPWebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;

@Controller
public class MessageController{
    @MessageMapping("/chat/{chatId}")
    @SendTo("/topic/messages/{chatId}")
    public OutputMessage send(@DestinationVariable String chatId, Message message) throws Exception{
        System.out.println("Output message: ");
        System.out.println(message.getMessage());
        System.out.println(message.getAuthor());
        Thread.sleep(1000);
        LocalDateTime timeStamp = LocalDateTime.now();
        System.out.println("Sending output message");
        return new OutputMessage(message.getMessage(), message.getAuthor(),timeStamp);
    }
}

