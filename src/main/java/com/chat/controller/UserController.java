package com.chat.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.chat.dao.FriendRepository;
import com.chat.entities.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

		return "/user/update";
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

		List<User> users =new ArrayList<>();


		List<User> requestUsers = new ArrayList<>();
		friendRepogetory.getFriendRequest(user.getId());
		List<Friend> requestList = friendRepogetory.getFriendRequest(user.getId());
		for (Friend f: requestList){
			requestUsers.add(userRepository.getReferenceById(f.getSender()));
		}
		model.addAttribute("requests", requestUsers);


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
	public String addImage(Model model, Principal principal, @RequestParam("profile_pic") MultipartFile file) {
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);

		if (file.isEmpty()) {
			System.out.println("no image Found");
		} else {
			user.setImageUrl(file.getOriginalFilename());

		}

		return "user/profile";
	}

	@PostMapping("/update_profile")
	public String handleProfileUpdate(
			@ModelAttribute User userForm, // Bind form data to the User object
			@RequestParam("profile") MultipartFile file,
			Model model, Principal principal) {
		// Fetch the user from the database using the authenticated user's username
		String username = principal.getName();
		User user = userRepository.getUserByUserName(username);

		if (user != null) {
			// Update fields like name, surname, mobile number based on form data
			user.setFirstname(userForm.getFirstname());
			user.setLastname(userForm.getLastname());
			user.setContact(userForm.getContact());

			// Save the updated user object to the database
			userRepository.save(user);
		}

		// Process the uploaded profile picture
		if (!file.isEmpty()) {
			try {
				// Generate a unique filename based on the user's data
				String filename = user.getFirstname() + "_" + user.getLastname() + "_" + user.getId() + ".png";

				// Specify the path where you want to save the image (e.g., a directory on your
				// server)
				String imagePath = "C:\\Users\\ACER NITRP-5\\Downloads\\chats\\mychat\\src\\main\\resources\\staticC:\\Users\\shubh\\Documents\\workspace-spring-tool-suite-4-4.19.1.RELEASE\\mychats\\src\\main\\resources\\static\\IMG\\\\" + filename;

				String imgPath = "/IMG/"+filename;

				// Save the uploaded profile picture to the specified path
				try {
					file.transferTo(new File(imagePath));
				} catch (Exception e) {
					System.out.println("Error saving file: " + e.getMessage());
				}
				System.out.println("Profile picture saved");

				// Optionally, you can save the file path to the user's profile
				user.setImageUrl(imgPath);
				userRepository.save(user);
			} catch (Exception e) {
				// Handle file processing errors
				model.addAttribute("error", "Failed to upload profile picture: " + e.getMessage());
				return "user/update";
			}
		}

		// Redirect to a success page or back to the profile update view
		return "redirect:/user/profile";
	}

}
