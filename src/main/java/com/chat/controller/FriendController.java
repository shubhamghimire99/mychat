package com.chat.controller;

import com.chat.dao.FriendRepogetory;
import com.chat.dao.UserRepository;
import com.chat.entities.Friend;
import com.chat.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class FriendController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRepogetory friendRepogetory;

    @PostMapping("/send_request")
    public String sendRequest(@RequestParam("reciver") Integer id, Principal principal){
        User receiver =userRepository.getReferenceById(id);
        User sender = userRepository.getUserByUserName(principal.getName());
        Friend friend = new Friend();
        friend.setReceiver(id);
        friend.setSender(sender.getId());
        friend.setStatus("PENDING");
        friendRepogetory.save(friend);
        System.out.println(id);
        return "redirect:/user/profile";
    }
}
