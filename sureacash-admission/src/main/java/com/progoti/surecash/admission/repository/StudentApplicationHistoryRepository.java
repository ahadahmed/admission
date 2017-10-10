package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.StudentApplicationHistory;
import com.progoti.surecash.admission.domain.Unit;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.dto.AdminDashboardDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Shaown on 12:03 PM.
 */
@Repository
public interface StudentApplicationHistoryRepository extends JpaRepository<StudentApplicationHistory, Integer>{
    StudentApplicationHistory findOneByApplicationIdAndUniversity(String applicantId, University university);

    @Query(value = "SELECT NEW com.progoti.surecash.dto.AdminDashboardDto(u.id, u.name, COUNT(sah.id), " +
            "COUNT(CASE WHEN sah.paid IS NOT NULL THEN TRUE END)) " +
            "FROM StudentApplicationHistory sah " +
            "INNER JOIN sah.unit u " +
            "WHERE sah.university = :university " +
            "GROUP BY u.id")
    List<AdminDashboardDto> findUniversityStatus(@Param("university") University university);

    @EntityGraph(value = "StudentApplicationHistory.detail", type = EntityGraph.EntityGraphType.LOAD)
    List<StudentApplicationHistory> findAllByUniversityAndUnit(University university, Unit unit);
}
