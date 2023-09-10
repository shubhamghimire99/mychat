package com.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.chat.entities.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.chat.dao.MessageRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ObjectMapper objectMapper;

    private final MessageRepository messageRepository;
    // final static List<WebSocketSession> sessions = new HashMap<>();
    final static HashMap<String, WebSocketSession> sessions = new HashMap<>();

    public ChatWebSocketHandler(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public ChatWebSocketHandler() {
        this.messageRepository = null;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Principal principal = session.getPrincipal();
        System.out.println(principal.getName());
        sessions.put(principal.getName(), session);

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        String payload = textMessage.getPayload();
        // Assuming the payload contains sender information and the message

        // Parse the payload and extract sender and message
        String[] parts = payload.split(":");
        String sender = parts[0];
        String messageText = parts[1];

        // Create a Messages entity and save it to the database
        Messages message = new Messages();
        message.setSender(sender);
        message.setMessage(messageText);
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);

        String messageJson;
        try {
            messageJson = objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            messageJson = "{'error': 'JSON serialization error'}";
        }
        List<WebSocketSession> allSessions = (List<WebSocketSession>) sessions.values();
        // Broadcast the message to all connected users
        for (WebSocketSession currentSession : allSessions) {
            if (currentSession.isOpen()) {
                currentSession.sendMessage(new TextMessage(messageJson));
            }
        }
    }
}
