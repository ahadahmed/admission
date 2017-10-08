package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.AdmissionPaymentRequest;
import com.progoti.surecash.admission.domain.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Shaown on 4:32 PM.
 */
@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer>{
    TransactionHistory findOneByAdmissionPaymentRequest(AdmissionPaymentRequest admissionPaymentRequest);
}
