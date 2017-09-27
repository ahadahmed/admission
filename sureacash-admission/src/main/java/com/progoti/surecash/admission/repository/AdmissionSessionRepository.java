package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.AdmissionSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shaown on 12:01 PM.
 */
@Repository
public interface AdmissionSessionRepository extends JpaRepository<AdmissionSession, Integer>{
}
