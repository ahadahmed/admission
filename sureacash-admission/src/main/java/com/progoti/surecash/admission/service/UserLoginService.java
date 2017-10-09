package com.progoti.surecash.admission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.domain.User;
import com.progoti.surecash.admission.domain.UserDetailsImpl;
import com.progoti.surecash.admission.repository.StudentInfoRepository;
import com.progoti.surecash.admission.repository.UniversityRepository;

@Service
public class UserLoginService implements UserDetailsService{
	@Autowired
	StudentInfoRepository studentInfoRepository;
	@Autowired
	UniversityRepository univeRepository;
	
	

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		String[] usernameAndUnivid = userName.trim().split(":");
		University university = univeRepository.findOne(Integer.valueOf(usernameAndUnivid[1]));
		StudentInfo student = studentInfoRepository.findOneByUserNameAndUniversity(usernameAndUnivid[0], university);
		
		User user = new User();
		user.setUserName(student.getUserName());
		user.setPassword(student.getPassword());
		user.setRole("USER");
		user.setEmail(student.getEmail());
		user.setUniv(university);
		return new UserDetailsImpl(user);
	}
	
	
	
	
	
	

}
