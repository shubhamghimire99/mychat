package com.chat.controller;

import com.chat.dao.FriendRepository;
import com.chat.dao.GroupMemberRepository;
import com.chat.dao.RoomRepository;
import com.chat.dao.UserRepository;
import com.chat.entities.Friend;
import com.chat.entities.GroupMembers;
import com.chat.entities.Room;
import com.chat.entities.User;

import jakarta.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class FriendController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @PostMapping("/send_request")
    public String sendRequest(@RequestParam("reciver") Integer id, Principal principal){
        User receiver =userRepository.getReferenceById(id);
        User sender = userRepository.getUserByUserName(principal.getName());
        Friend checkrequest =friendRepository.checkRequest(sender.getId(),receiver.getId());
        if(checkrequest!=null){
            
        	return "redirect:/user/friends";
        } 
        
        Friend friend = new Friend();
        friend.setReceiver(id);
        friend.setSender(sender.getId());
        friend.setStatus("PENDING");
        friendRepository.save(friend);
        System.out.println(id);
        return "redirect:/user/friends"; 
        
    }
    
    @PostMapping("/acceptRequest")
    public String acceptRequest(@RequestParam("id") Integer senderId, Principal principal){
        User receiver = userRepository.getUserByUserName(principal.getName());
        Friend getRequest = friendRepository.getRequestBySenderAndReceiverID(senderId,receiver.getId());
        getRequest.setStatus("ACCEPTED");
        friendRepository.save(getRequest);

        // create message new room with room name such that both the users id concatinated together
        String roomName = senderId.toString()+ "_" + receiver.getId();
        
        Room room = roomRepository.getRoomByRoomName(roomName);
        if(room == null){
            room = new Room();
            room.setGroup_name(roomName);
            room.setAdmin(0);
            roomRepository.save(room);
        }

        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setRoom(room);
        groupMembers.setUser(receiver);
        groupMemberRepository.save(groupMembers);

        groupMembers = new GroupMembers();
        groupMembers.setRoom(room);
        groupMembers.setUser(userRepository.getReferenceById(senderId));
        groupMemberRepository.save(groupMembers);

        return "redirect:/user/notification";
    }

}
