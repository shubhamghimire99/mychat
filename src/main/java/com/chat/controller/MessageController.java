package com.chat.controller;

import com.chat.entities.Messages;
import com.chat.entities.User;
import com.chat.dao.MessageRepository;
import com.chat.dao.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;
    
	@PostMapping("/send")
	public String registerUser(@ModelAttribute Messages message,BindingResult result1,
    @RequestParam(value = "aggrement", defaultValue = "false")
    boolean aggrement, Model model, Authentication authentication) {
        
        
        if(result1.hasErrors()) {
            System.out.println("ERROR"+ result1.toString());
			model.addAttribute("message",message);
			return	"register";
		}

        String loggedInUserEmail = authentication.getName();
            
        // Find the user by email
        User loggedInUser = this.userRepository.getUserByUserName(loggedInUserEmail);
        if (loggedInUser == null) {
            // Handle the case where the user is not found
            return "error";
        }
		
		message.setSender(loggedInUser.getFirstname());
		message.setTimestamp(java.time.LocalDateTime.now());
		
		Messages result = this.messageRepository.save(message);
		
		model.addAttribute("message",result);
		
		return "redirect:/user/notification";
	}

    // You can add more endpoints for updating, deleting, etc.
}
