package com.chat.dao;

import com.chat.entities.Messages;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Messages, Integer> {
    List<Messages> findBySender(String sender);

    // list message by room_id
    @Query("SELECT ms FROM Messages ms WHERE ms.room_id = :room_id")
    List<Messages> findByRoomId(int room_id);
}
