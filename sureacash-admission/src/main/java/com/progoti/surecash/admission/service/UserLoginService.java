package com.progoti.surecash.admission.service;

import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.domain.User;
import com.progoti.surecash.admission.domain.UserDetailsImpl;
import com.progoti.surecash.admission.repository.StudentInfoRepository;
import com.progoti.surecash.admission.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService implements UserDetailsService {

    private StudentInfoRepository studentInfoRepository;
    private UniversityRepository univeRepository;

    @Autowired
    public UserLoginService(StudentInfoRepository studentInfoRepository,
            UniversityRepository univeRepository) {
        this.studentInfoRepository = studentInfoRepository;
        this.univeRepository = univeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String[] usernameAndUnivid = userName.trim().split(":");
        University university = univeRepository.findOne(Integer.valueOf(usernameAndUnivid[1]));
        StudentInfo student = studentInfoRepository
                .findOneByUserNameAndUniversity(usernameAndUnivid[0], university);
        if (student == null) {
            return null;
        }

        User user = new User();
        user.setUserId(student.getId());
        user.setUserName(student.getUserName());
        user.setPassword(student.getPassword());
        user.setRole("USER");
        user.setEmail(student.getEmail());
        user.setUniv(university);
        
        if(user.getUserName().equalsIgnoreCase("admin")) {
        	user.setRole("ADMIN");
        }
        return new UserDetailsImpl(user);
    }


}
