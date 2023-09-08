package com.chat.dao;

import com.chat.entities.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend,Integer> {

    @Query("SELECT f FROM Friend f WHERE (f.receiver = :id OR f.sender = :id) AND f.status = 'ACCEPTED' ")
    public List<Friend> getFriends(@Param("id") Integer id);
    
    @Query("select f from Friend f where f.receiver = :receiver and f.sender = :sender")
    public Friend getRequestBySenderAndReceiverID(@Param("sender")Integer sender,@Param("receiver")Integer receiver);

    @Query("select f from Friend f where (f.receiver = :id or f.sender = :id) and f.status = 'PENDING'")
    public List<Friend> getFriendRequest(int id);

    @Query("select f from Friend f where (f.sender = :sender and f.receiver = :receiver) or (f.sender = :receiver and f.receiver = :sender)")
    public Friend checkRequest(@Param("sender")Integer sender,@Param("receiver")Integer receiver);

}
