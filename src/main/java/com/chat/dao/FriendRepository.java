package com.chat.dao;

import com.chat.entities.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend,Integer> {
    @Query("select f from Friend f where f.receiver = :id and f.status = 'PENDING'")
    public List<Friend> getFriendRequest(@Param("id")Integer id);
    @Query("select f from Friend f where f.receiver = :receiver and f.sender = :sender")
    public Friend getRequestBySenderAndReceiverID(@Param("sender")Integer sender,@Param("receiver")Integer receiver);

}
