package com.chat.controller;

import com.chat.entities.Messages;
import com.chat.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.chat.dao.GroupMemberRepository;
import com.chat.dao.MessageRepository;
import com.chat.dao.UserRepository;

import java.io.IOException;
import java.security.Principal;

import javax.swing.GroupLayout.Group;

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

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @PostMapping("/sendChat")
    public String sendChat(@ModelAttribute Messages message,
                              BindingResult result1,
                              Model model,
                              Authentication authentication,
                              Principal principal,
                              @RequestParam("receiver") int receiver) {
        
        if (result1.hasErrors()) {
            model.addAttribute("message", message);
            return "user/chat";
        }

        String loggedInUserEmail = authentication.getName();
            
        // Find the user by email
        User loggedInUser = this.userRepository.getUserByUserName(loggedInUserEmail);
        
        // get room_id from group_members table using 2 user_ids
//        int room_id = groupMemberRepository.getRoomId(loggedInUser.getId(), receiver);
//        System.out.println("room_id: " + room_id);

        message.setSender(loggedInUser.getEmail());
        message.setSenderId(loggedInUser.getId());
        message.setImageUrl(loggedInUser.getImageUrl());
//        message.setRoom_id(room_id);
        message.setTimestamp(java.time.LocalDateTime.now());
        
        Messages result = this.messageRepository.save(message);

        String messageJson;
        try{
            messageJson = objectMapper.writeValueAsString(result);
        } catch(Exception e) {
            messageJson = "{'error': 'JSON serialization error'}";
        }
        User rec = userRepository.getReferenceById(receiver);


        WebSocketSession session = ChatWebSocketHandler.sessions.get(rec.getEmail());
        try {
            session.sendMessage(new TextMessage(messageJson));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/user/chat?userId=" + receiver;
    }
}
