package com.chat.controller;

import com.chat.entities.ChatMessage;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Controller
public class ChatController {

    // Define a map to manage chat rooms with room_id as the key
    private Map<Integer, Set<WebSocketSession>> chatRooms = new ConcurrentHashMap<>();


    // This method is used to send messages to the chat room
    @MessageMapping("/chat.send/{room_id}")
    public void sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable int room_id) {
        Set<WebSocketSession> roomSessions = chatRooms.get(room_id);

        if (roomSessions != null) {
            // Broadcast the message to all users in the room
            for (WebSocketSession session : roomSessions) {
                if (session.isOpen()) {
                    try {
                        session.sendMessage(new TextMessage(chatMessage.getContent()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // This method is used to join a chat room
    @MessageMapping("/chat.join")
    public void joinRoom(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        int room_id = chatMessage.getRoomId();

        WebSocketSession session = (WebSocketSession) headerAccessor.getSessionAttributes().get("webSocketSession");

        // Add the session to the chat room
        chatRooms.computeIfAbsent(room_id, key -> ConcurrentHashMap.newKeySet()).add(session);

        // You may also want to update the user's session with the room_id for future reference
    }



    // @MessageMapping("/chat.send")
    // public void sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    //     int room_id = chatMessage.getRoomId();
    //     Set<WebSocketSession> roomSessions = chatRooms.get(room_id);

    //     if (roomSessions != null) {
    //         // Broadcast the message to all users in the room
    //         for (WebSocketSession session : roomSessions) {
    //             if (session.isOpen()) {
    //                 try {
    //                     session.sendMessage(new TextMessage(chatMessage.getContent()));
    //                 } catch (IOException e) {
    //                     e.printStackTrace();
    //                 }
    //             }
    //         }
    //     }
    // }

    // @MessageMapping("/chat.join")
    // public void joinRoom(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    //     int room_id = chatMessage.getRoomId();

    //     WebSocketSession session = new headerAccessor.getSessionId();


    //     // Add the session to the chat room
    //     chatRooms.computeIfAbsent(room_id, key -> ConcurrentHashMap.newKeySet()).add(session);

    //     // You may also want to update the user's session with the room_id for future reference
    // }
}
