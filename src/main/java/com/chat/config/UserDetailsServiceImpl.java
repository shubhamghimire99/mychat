package com.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.chat.config.CustomUserDetails;
import com.chat.dao.UserRepository;
import com.chat.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user =userRepository.getUserByUserName(username);
		if(user==null) {
			throw new UsernameNotFoundException("Couldn not found user!!");
		}
		
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		return customUserDetails;
	}
	
	
}