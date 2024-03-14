package com.springsecurity.test.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.springsecurity.test.entity.UserInfo;
import com.springsecurity.test.reository.UserRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService
{

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException 
	{
		Optional<UserInfo>userInfo=userRepository.findByEmail(email);
		return userInfo.map(UserInfoUserDetails::new).orElseThrow(()->new UsernameNotFoundException("user not found"+email));	
		}

}
