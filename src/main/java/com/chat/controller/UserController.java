package com.chat.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chat.dao.UserRepository;
import com.chat.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping("/profile")
	public String UserPofile(Model model,Principal principal ) {
		model.addAttribute("title", "User Profile");
		String username= principal.getName();
		System.out.println("Username "+username);
		//the user using username
		User user= userRepository.getUserByUserName(username);
		System.out.println("User "+user);
		
		model.addAttribute("user",user);
		
		return "/user/user_profile";
	}
	
	
	@RequestMapping("/chat")
	public String chat(Model model ,Principal principal) {
		model.addAttribute("title", "Chats");
		String username= principal.getName();
		User user= userRepository.getUserByUserName(username);
		model.addAttribute("user",user);
		return "/user/chat";
	}
	@RequestMapping("/groupchat")
	public String groupchat(Model model, Principal principal) {
		model.addAttribute("title", "Group Chat");
		String username= principal.getName();
		User user= userRepository.getUserByUserName(username);
		model.addAttribute("user",user);
		return "/user/groupchat";
	}
	@RequestMapping("/notification")
	public String notification(Model model, Principal principal) {
		model.addAttribute("title", "Notification");
		String username= principal.getName();
		User user= userRepository.getUserByUserName(username);
		model.addAttribute("user",user);
		return "/user/notification";
	}
}
