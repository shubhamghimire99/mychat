package com.chat.controller;

import com.chat.entities.GroupMembers;
import com.chat.entities.Messages;
import com.chat.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.chat.dao.GroupMemberRepository;
import com.chat.dao.MessageRepository;
import com.chat.dao.UserRepository;

import java.io.IOException;
import java.security.Principal;

import javax.swing.GroupLayout.Group;

import java.util.ArrayList;
import java.util.List;
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
            @RequestParam(value = "receiver", defaultValue = "0") int receiver,
            @RequestParam(value = "groupId", defaultValue = "0") int groupId) {

        if (result1.hasErrors()) {
            model.addAttribute("message", message);
            return "user/chat";
        }

        String loggedInUserEmail = authentication.getName();

        // Find the user by email
        User loggedInUser = this.userRepository.getUserByUserName(loggedInUserEmail);

        message.setSender(loggedInUser.getEmail());
        message.setSenderId(loggedInUser.getId());
        message.setImageUrl(loggedInUser.getImageUrl());

        if (groupId != 0) {

            message.setRoom_id(groupId);
        } else {

            int room_id = groupMemberRepository.getRoomId(loggedInUser.getId(), receiver);
            message.setRoom_id(room_id);
        }
        message.setTimestamp(java.time.LocalDateTime.now());

        Messages result = this.messageRepository.save(message);
        if (groupId == 0) {
            String messageJson;
            try {
                messageJson = objectMapper.writeValueAsString(result);
            } catch (Exception e) {
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
        }else{
            String messageJson;
            try {
                messageJson = objectMapper.writeValueAsString(result);
            } catch (Exception e) {
                messageJson = "{'error': 'JSON serialization error'}";
            }
            List<GroupMembers> members = groupMemberRepository.getAllMembersByRoomId(groupId);
            for(GroupMembers gm: members){
                System.out.println(gm.getUser().getUname());
                WebSocketSession session = ChatWebSocketHandler.sessions.get(gm.getUser().getEmail());
                if(session != null){

                    try {
                        session.sendMessage(new TextMessage(messageJson));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return "redirect:/user/groupchat?roomId=" + groupId;
        }



    }

}
