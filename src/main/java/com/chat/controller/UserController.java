package com.chat.controller;

import java.security.Principal;
import java.util.List;

import com.chat.dao.FriendRepository;
import com.chat.entities.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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


	@RequestMapping("/chat")
	public String chat(Model model, Principal principal) {
		model.addAttribute("title", "Chats");
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);

		// Fetch all users except the logged-in user
		List<User> allUsers = userRepository.findAll();
		allUsers.remove(user); // Remove the logged-in user from the list

		model.addAttribute("user", user);
		model.addAttribute("allUsers", allUsers);


		// Assuming your MessageRepository has a method to fetch messages by sender
		List<Messages> userMessages = messageRepository.findAll();

		model.addAttribute("user", user);
		model.addAttribute("userMessages", userMessages);

		return "user/chat";
	}

	@RequestMapping("/groupchat")
	public String groupchat(Model model, Principal principal) {
		model.addAttribute("title", "Group Chat");
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);
		model.addAttribute("user", user);
		return "/user/groupchat";
	}

	@Autowired
	private FriendRepository friendRepogetory;

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

		friendRepogetory.getFriendRequest(user.getId());
		List<Friend> requestList = friendRepogetory.getFriendRequest(user.getId());
		model.addAttribute("requests",requestList);
		
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
		model.addAttribute("allUsers", allUsers);
		return "user/friend";
	}

	@PostMapping("/add_image")
	public String addImage(Model model, Principal principal , @RequestParam("profileS")MultipartFile file){
		String username = principal.getName();
		User user= userRepository.getUserByUserName(username);
		return "user/profile";
	}
}
