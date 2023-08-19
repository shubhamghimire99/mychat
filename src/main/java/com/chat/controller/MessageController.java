package com.chat.controller;

import com.chat.entities.Messages;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import com.chat.dao.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

	@PostMapping("/send")
	public String registerUser(@Valid @ModelAttribute("message") Messages message,BindingResult result1,
			@RequestParam(value = "aggrement", defaultValue = "false")
			boolean aggrement, Model model) {
		
		if(result1.hasErrors()) {
			System.out.println("ERROR"+ result1.toString());
			model.addAttribute("message",message);
			return	"register";
		}
		
		message.setSender("pritam");
		message.setTimestamp(java.time.LocalDateTime.now());
		
		Messages result = this.messageRepository.save(message);
		
		model.addAttribute("message",result);
		
		return "redirect:/user/notification";
	}

    // You can add more endpoints for updating, deleting, etc.
}
