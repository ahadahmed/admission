package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.AdmissionSession;
import com.progoti.surecash.admission.domain.Unit;
import com.progoti.surecash.admission.domain.University;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Shaown on 12:01 PM.
 */
@Repository
public interface AdmissionSessionRepository extends JpaRepository<AdmissionSession, Integer>{
    AdmissionSession findOneByUnit(Unit unit);
    List<AdmissionSession> findAllBySessionAndUnitIn(String session, List<Unit> unitList);

    @EntityGraph(value = "AdmissionSession.detail", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT a FROM AdmissionSession a INNER JOIN a.unit u WHERE u.university = :university ORDER BY a.session DESC, a.unit ASC")
    List<AdmissionSession> findAllUnitByUniversity(@Param("university") University university);
}
