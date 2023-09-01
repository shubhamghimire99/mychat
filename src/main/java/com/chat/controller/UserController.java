package com.chat.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

import com.chat.dao.MessageRepository;
import com.chat.dao.UserRepository;
import com.chat.entities.Messages;
import com.chat.entities.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MessageRepository messageRepository;

	@RequestMapping("/profile")
	public String UserPofile(Model model, Principal principal) {
		model.addAttribute("title", "User Profile");
		String username = principal.getName();
		System.out.println("Username " + username);
		// the user using username
		User user = userRepository.getUserByUserName(username);
		System.out.println("User " + user);

		model.addAttribute("user", user);

		return "/user/user_profile";
	}
		@RequestMapping("/Update")
	public String updateprofile(Model model, Principal principal) {
		model.addAttribute("title", "Update your profile");
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);

		model.addAttribute("user", user);
		return "user/update";
	}

	@RequestMapping("/chat")
	public String chat(Model model, Principal principal) {
		model.addAttribute("title", "Chats");
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);
		model.addAttribute("user", user);
		return "/user/chat";
	}

	@RequestMapping("/groupchat")
	public String groupchat(Model model, Principal principal) {
		model.addAttribute("title", "Group Chat");
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);
		model.addAttribute("user", user);
		return "/user/groupchat";
	}

	@RequestMapping("/notification")
	public String notification(Model model, Principal principal) {
		model.addAttribute("title", "Notification");
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);

		// Assuming your MessageRepository has a method to fetch messages by sender
		List<Messages> userMessages = messageRepository.findAll();

		model.addAttribute("user", user);
		model.addAttribute("userMessages", userMessages); // Pass the messages to the template

		return "user/notification"; // Remove the leading slash
	}

	@RequestMapping("/cheat")
	public String chatPage(Model model, Principal principal) {

		model.addAttribute("title", "Chat");
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);

		// Assuming your MessageRepository has a method to fetch messages by sender
		List<Messages> userMessages = messageRepository.findAll();

		model.addAttribute("user", user);
		model.addAttribute("userMessages", userMessages); // Pass the messages to the template

		return "user/notification"; // Remove the leading slash
	}

	@RequestMapping("/friends")
	public String friends(Model model, Principal principal) {
		model.addAttribute("title", "Friend Request");
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);

		// Fetch all users except the logged-in user
        List<User> allUsers = userRepository.findAll();
        allUsers.remove(user); // Remove the logged-in user from the list

		model.addAttribute("user", user);
		return "user/friend";
	}
}
