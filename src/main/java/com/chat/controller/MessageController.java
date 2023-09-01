package com.chat.controller;

import com.chat.entities.Messages;
import com.chat.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.chat.dao.MessageRepository;
import com.chat.dao.UserRepository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Controller
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/send")
    public String sendMessage(@ModelAttribute Messages message,
                              BindingResult result1,
                              Model model,
                              Authentication authentication) {
        
        if (result1.hasErrors()) {
            model.addAttribute("message", message);
            return "chat";
        }

        String loggedInUserEmail = authentication.getName();
            
        // Find the user by email
        User loggedInUser = this.userRepository.getUserByUserName(loggedInUserEmail);
        
        message.setSender(loggedInUser.getFirstname());
        message.setTimestamp(java.time.LocalDateTime.now());
        
        Messages result = this.messageRepository.save(message);

        String messageJson;
        try{
            messageJson = objectMapper.writeValueAsString(result);
        } catch(Exception e) {
            messageJson = "{'error': 'JSON serialization error'}";
        }
        
        // Broadcast the message to all connected users
        for (WebSocketSession session : ChatWebSocketHandler.sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(messageJson));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return "redirect:/user/notification"; // Redirect back to the chat page
    }

    @PostMapping("/sendChat")
    public String sendChat(@ModelAttribute Messages message,
                              BindingResult result1,
                              Model model,
                              Authentication authentication) {
        
        if (result1.hasErrors()) {
            model.addAttribute("message", message);
            return "chat";
        }

        String loggedInUserEmail = authentication.getName();
            
        // Find the user by email
        User loggedInUser = this.userRepository.getUserByUserName(loggedInUserEmail);
        
        message.setSender(loggedInUser.getFirstname());
        message.setTimestamp(java.time.LocalDateTime.now());
        
        Messages result = this.messageRepository.save(message);

        String messageJson;
        try{
            messageJson = objectMapper.writeValueAsString(result);
        } catch(Exception e) {
            messageJson = "{'error': 'JSON serialization error'}";
        }
        
        // Broadcast the message to all connected users
        for (WebSocketSession session : ChatWebSocketHandler.sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(messageJson));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return "redirect:/user/chat"; // Redirect back to the chat page
    }
}
