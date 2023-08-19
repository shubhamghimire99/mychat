package com.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.chat.entities.User;

@Controller
public class MainController {

	@RequestMapping("/")
	public String register(Model model) {
		model.addAttribute("title", "Register-MyChats");
		model.addAttribute("user", new User());
		return "register";
	}

	@GetMapping("/login")
	public String Login(Model model) {
		model.addAttribute("title","LOGIN-MyChats");
		return "Login";
	}
	@GetMapping("/forget")
	public String forgetPassword(Model model) {
		model.addAttribute("title","ForgetPassword-MyChats");
		return "forget_password";
	}

}
