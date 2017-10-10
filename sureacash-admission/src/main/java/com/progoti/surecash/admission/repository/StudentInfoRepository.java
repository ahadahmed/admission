package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.domain.University;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shaown on 12:05 PM.
 */
@Repository()
public interface StudentInfoRepository extends JpaRepository<StudentInfo, Integer>{
	StudentInfo findOneByUserNameAndUniversity(String userName, University university);
}
