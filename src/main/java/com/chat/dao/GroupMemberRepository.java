package com.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chat.entities.GroupMembers;

public interface GroupMemberRepository extends JpaRepository<GroupMembers, Integer>{

    // return room_id from group_members table using 2 user_ids
    @Query("select gm.room_id from GroupMembers gm where (gm.user_id = :user_id1 or gm.user_id = :user_id2) group by gm.room_id having count(gm.room_id) = 2")
    public int getRoomId(int user_id1, int user_id2);

}
