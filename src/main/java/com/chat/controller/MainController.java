package com.chat.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chat.dao.UserRepository;
import com.chat.entities.User;
import com.chat.service.EmailService;
import javax.swing.text.html.HTML;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;

@Controller
public class MainController {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepository userRepository;

	Random random = new Random(1000);

	@RequestMapping("/")
	public String register(Model model) {
		model.addAttribute("title", "Register-MyChats");
		model.addAttribute("user", new User());
		return "register";
	}

	@GetMapping("/login")
	public String Login(Model model) {
		model.addAttribute("title", "LOGIN-MyChats");
		return "Login";
	}

	@RequestMapping("/forget")
	public String forgetPassword(Model model) {
		model.addAttribute("title", "ForgetPassword-MyChats");
		return "forget_password";
	}

	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email, HttpSession session) {

		System.out.println("Email" + email);

		Random random = new Random();
		int otp = 1000 + random.nextInt(9000);

		System.out.println("OTP " + otp);

		String subject = "OTP from MyChats";

		String message = ""
				+ "<div style='border:1px solid #e2e2e2; padding:20px'>"
				+ "<h1>"
				+ "OTP is "
				+ "<b>" + otp
				+ "</n>"
				+ "</h1>"
				+ "</div>";

		String to = email;

		boolean flag = this.emailService.sendEmail(subject, message, to);

		if (flag) {
			session.setAttribute("myotp", otp);
			session.setAttribute("email", email);
			return "Verify_OTP";

		} else {
			session.setAttribute("message", "check your Email");
			return "forget_password";
		}

	}

	// verify otp
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam("otp") int otp, HttpSession session) {
		int myOtp = (int) session.getAttribute("myotp");
		String email = (String) session.getAttribute("email");
		if (myOtp == otp) {
			User user = this.userRepository.getUserByUserName(email);
			if (user == null) {
				session.setAttribute("message", "User does not exist with this email");
				return "forget_password";
			} else {
				// send password change form
				return "password_change_form";
			}

		} else {
			session.setAttribute("message", "You have entered wrong otp");
			return "Verify_OTP";
		}

	}

	// change password
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("newpassword") String newpassword, HttpSession session) {
		String email = (String) session.getAttribute("email");
		User user = this.userRepository.getUserByUserName(email);
		user.setPassword(this.passwordEncoder.encode(newpassword));
		this.userRepository.save(user);
		return "redirect:/login?change=password changed successfully..";
	}
}
