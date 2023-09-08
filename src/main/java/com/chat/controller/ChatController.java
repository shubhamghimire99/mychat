package com.chat.controller;

import com.chat.entities.ChatMessage;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    // @MessageMapping("/groupchat")
    // @SendTo("/topic/chat")
    // public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    //     headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    //     return chatMessage;
    // }

    @MessageMapping("/chat/(userid={userid})")
    @SendTo("/topic/{group_name}")
    public ChatMessage sendMessage(@DestinationVariable String group_name, @Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}