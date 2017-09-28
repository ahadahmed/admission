package com.progoti.surecash.admission.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.progoti.surecash.admission.domain.User;
import com.progoti.surecash.admission.domain.UserDetailsImpl;

@Service
public class UserLoginService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		User user = new User();
		user.setUserName("testUser");
		user.setPassword("123456");
		user.setRole("USER");
		user.setEmail("test@mail.com");
		
		return new UserDetailsImpl(user);
	}
	
	

}
