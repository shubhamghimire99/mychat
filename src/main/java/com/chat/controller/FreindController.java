package com.chat.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chat.dao.FriendRepository;
import com.chat.dao.UserRepository;
import com.chat.entities.Freind;
import com.chat.entities.User;

@Controller
public class FreindController {
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
	private UserRepository userRepository;


    @PostMapping("/send_request")
    public String sendRequst(Model model,@RequestParam("id") Integer id, Principal principle){
        String username = principle.getName();
		User user = userRepository.getUserByUserName(username);
        
        Freind friend = new Freind();
        friend.setSender(user.getId());
        friend.setReciver(id);
        friend.setStatus("pending");

        Freind result = this.friendRepository.save(friend);
		
        model.addAttribute("friend",result);

        return "redirect:/user/friend";
    }
    
}
