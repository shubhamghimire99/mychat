package com.chat.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chat.entities.Room;

public interface RoomRepository extends JpaRepository<Room ,Integer> {

    // to get room by group name
    @Query("select r from Room r where r.group_name = :roomName")
    public Room getRoomByRoomName(String roomName);

    // list all rooms by admin id
    @Query("select r from Room r where r.admin = :adminId")
    List<Room> getRoomsByAdminId(Integer adminId);

    // list rooms by roomId
    @Query("select r from Room r where r.id = :roomId")
    Room getRoomByRoomId(Integer roomId);


}
