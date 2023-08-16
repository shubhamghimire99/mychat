package com.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.chat.dao.UserRepository;
import com.chat.entities.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MainController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncorder;
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
	public String register(Model model) {
		model.addAttribute("title", "Register-MyChats");
		model.addAttribute("user", new User());
		return "register";
	}

	// handler for regeristing user
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1,
			@RequestParam(value = "aggrement", defaultValue = "false")
			boolean aggrement, Model model,HttpSession Session) {
		
		if(result1.hasErrors()) {
			System.out.println("ERROR"+ result1.toString());
			model.addAttribute("user",user);
			return	"register";
		}
		
		user.setRole("ROLE_USER");
		user.setEnabled(true);
		user.setImageUrl("default.png");
		user.setPassword(passwordEncorder.encode(user.getPassword()));
		
		User result = this.userRepository.save(user);
		
		model.addAttribute("user",result);
		
		return "login";
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
