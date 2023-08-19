package com.chat.dao;

import com.chat.entities.Messages;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Messages, Integer> {
    List<Messages> findBySender(String sender);
}
