package com.application.chat.controllers;

import com.application.chat.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController
{
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/message")   //  /app/message
    @SendTo("/chatroom/public")
    public Message receivePublicMessage(@Payload Message message){
        return message;
    }
    @MessageMapping("/private-message")  //  /app/private-message
    public Message receivePrivateMessage(@Payload Message message){
        //   /user/userName/private
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        System.out.println("Mesaj: "+ message + " catre: "+ message.getReceiverName());
        return message;
    }
}
