package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shaown on 5:13 PM.
 */
@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry, Integer>{
}
