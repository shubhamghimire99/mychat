package com.chat.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chat.entities.GroupMembers;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMembers, Integer> {

        @Query("select gm.room.id from GroupMembers gm " +
                        "inner join gm.room r " +
                        "where (gm.user.id = :user_id1 or gm.user.id = :user_id2) " +
                        "and r.admin = 0 " +
                        "group by gm.room.id having count(gm.room.id) = 2")
        public int getRoomId(int user_id1, int user_id2);

        // list room id by isGroup true and admin
        @Query("select gm.room.id from GroupMembers gm " +
                        "inner join gm.room r " +
                        "where gm.user.id = :user_id " +
                        "and gm.room.admin > 0 " +
                        "and isGroup = true")
        public List<Integer> getRoomIdFromUserId(int user_id);

        @Query("SELECT gm from GroupMembers gm WHERE gm.room.id = :roomId")
        public List<GroupMembers> getAllMembersByRoomId(@Param("roomId") int id);
}
