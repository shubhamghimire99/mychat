package com.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.entities.Room;

public interface RoomRepository extends JpaRepository<Room ,Integer> {

}
