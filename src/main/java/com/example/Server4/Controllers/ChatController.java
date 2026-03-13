package com.example.Server4.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
public class ChatController {


    @GetMapping("/api/getChat/{chatId}")
    public void connectUserToChat(@PathVariable String chatId){
        //If not logged in throw exception and return

        //If logged in: get all messages from the given chat.

    }

    @GetMapping("/")
    public void testMessage(){
        System.out.println("Test");
        //If not logged in throw exception and return

        //If logged in: get webSocket for given chat, and assign user id to the current chat session

    }
}
