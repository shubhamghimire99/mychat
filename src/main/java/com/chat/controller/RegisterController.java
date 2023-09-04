package com.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chat.dao.UserRepository;
import com.chat.entities.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
	private BCryptPasswordEncoder passwordEncorder;
	
	@Autowired
	private UserRepository userRepository;
    
	@PostMapping("/register")
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
		user.setImageUrl("/IMG/profile.png");
		user.setPassword(passwordEncorder.encode(user.getPassword()));
		
		User result = this.userRepository.save(user);
		
		model.addAttribute("user",result);
		
		return "login";
	}
}
