package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.AdmissionPaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shaown on 4:31 PM.
 */
@Repository
public interface AdmissionPaymentRequestRepository extends JpaRepository<AdmissionPaymentRequest, Integer>{
}
