package com.chat.dao;

import com.chat.entities.Friend;
import com.chat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepogetory extends JpaRepository<Friend,Integer> {
    @Query("select f from Friend f where f.receiver = :id and f.status = 'PENDING'")
    public List<Friend> getFriendRequest(@Param("id")Integer id);

}
