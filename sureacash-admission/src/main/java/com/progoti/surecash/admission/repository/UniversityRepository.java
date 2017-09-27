package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shaown on 12:09 PM.
 */
@Repository
public interface UniversityRepository extends JpaRepository<University, Integer>{
}
