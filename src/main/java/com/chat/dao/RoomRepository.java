package com.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chat.entities.Room;

public interface RoomRepository extends JpaRepository<Room ,Integer> {

    // to get room by group name
    @Query("select r from Room r where r.group_name = :roomName")
    public Room getRoomByRoomName(String roomName);

}
