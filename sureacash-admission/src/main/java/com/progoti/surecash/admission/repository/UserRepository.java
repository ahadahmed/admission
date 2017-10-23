package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findOneByUserNameAndUniv(String userName, University university);

    @Query("select u from User u join fetch u.studentId where u.studentId = :studentInfo")
	User findOneByStudentId(@Param("studentInfo") StudentInfo studentInfo);
}
