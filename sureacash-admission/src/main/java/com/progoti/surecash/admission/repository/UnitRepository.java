package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.AdmissionSession;
import com.progoti.surecash.admission.domain.Unit;
import com.progoti.surecash.admission.domain.University;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UnitRepository extends JpaRepository<Unit, Integer> {

    List<Unit> findAllByUniversity(University university);

    @Query("select a from AdmissionSession a join a.unit u join u.university un where a.session = :session and un.id = :universityId")
    List<AdmissionSession> loadUnitsByUniversityAndSession(@Param("session") String session, @Param("universityId") int universityId);

    @Query("select a from AdmissionSession a join a.unit u join u.university un where a.session = :session and u.id in :unitIds")
    List<AdmissionSession> loadUnitsByUnitIds(@Param("session") String session, @Param("unitIds") List<Integer> unitIds);
}
