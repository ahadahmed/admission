package com.progoti.surecash.admission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.domain.User;
import com.progoti.surecash.admission.domain.UserDetailsImpl;
import com.progoti.surecash.admission.repository.StudentInfoRepository;

@Service
public class UserLoginService implements UserDetailsService{
	@Autowired
	StudentInfoRepository studentInfoRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		StudentInfo student = studentInfoRepository.findOneByUserName(userName);
		
		User user = new User();
		user.setUserName(student.getUserName());
		user.setPassword(student.getPassword());
		user.setRole("USER");
		user.setEmail(student.getEmail());
		
		return new UserDetailsImpl(user);
	}
	
	
	
	

}
