package com.progoti.surecash.admission.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserLoginService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		// TODO Have to implement authentication & authorization logic here
		return null;
	}

}
