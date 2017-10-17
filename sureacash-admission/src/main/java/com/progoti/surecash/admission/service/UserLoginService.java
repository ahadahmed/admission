package com.progoti.surecash.admission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.domain.User;
import com.progoti.surecash.admission.domain.UserDetailsImpl;
import com.progoti.surecash.admission.repository.UniversityRepository;
import com.progoti.surecash.admission.repository.UserRepository;

@Service
public class UserLoginService implements UserDetailsService {

    private UserRepository userRepository;
    private UniversityRepository univeRepository;

    @Autowired
    public UserLoginService(UserRepository userRepository,
            UniversityRepository univeRepository) {
        this.userRepository = userRepository;
        this.univeRepository = univeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String[] usernameAndUnivid = userName.trim().split(":");
        University university = univeRepository.findOne(Integer.valueOf(usernameAndUnivid[1]));
        User user = userRepository.findOneByUserNameAndUniv(usernameAndUnivid[0], university);
                
        if (user == null) {
            return null;
        }

        /*User user = new User();
        user.setUserId(user.getId());
        user.setUserName(user.getUserName());
        user.setPassword(user.getPassword());
        user.setRole("USER");
        user.setEmail(user.getEmail());
        user.setUniv(university);
        
        if(user.getUserName().equalsIgnoreCase("admin")) {
        	user.setRole("ADMIN");
        }*/
        
        return new UserDetailsImpl(user);
    }


}
