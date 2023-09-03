package com.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.entities.Freind;

public interface FriendRepository extends  JpaRepository<Freind, Integer> {
    
}
