package com.contact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.contact.entity.User;
import com.contact.repository.UserRepository;

@Service
public class HomeService 
{
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	public User AddUsertodatabase(User user)
	{
		user.setRole("ROLE_USER");
		user.setEnable(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User res=this.userRepository.save(user);
		return res;
	}
}
