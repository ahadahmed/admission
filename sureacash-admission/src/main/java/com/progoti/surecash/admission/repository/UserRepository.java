package com.progoti.surecash.admission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findOneByUserNameAndUniv(String userName, University university);
}
