package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.AdmissionSession;
import com.progoti.surecash.admission.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Shaown on 12:01 PM.
 */
@Repository
public interface AdmissionSessionRepository extends JpaRepository<AdmissionSession, Integer>{
    AdmissionSession findOneByUnit(Unit unit);
    List<AdmissionSession> findAllBySessionAndUnitIn(String session, List<Unit> unitList);
}
