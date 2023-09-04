package com.chat.controller;

import com.chat.dao.FriendRepository;
import com.chat.dao.UserRepository;
import com.chat.entities.Friend;
import com.chat.entities.User;
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

    @PostMapping("/send_request")
    public String sendRequest(@RequestParam("reciver") Integer id, Principal principal){
        User receiver =userRepository.getReferenceById(id);
        User sender = userRepository.getUserByUserName(principal.getName());
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
        return "redirect:/user/friends";
    }

}
