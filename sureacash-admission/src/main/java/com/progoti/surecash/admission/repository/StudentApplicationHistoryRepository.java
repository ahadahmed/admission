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

    @Query(value = "SELECT NEW com.progoti.surecash.dto.AdminDashboardDto(u.id, u.name, u.code, COUNT(sah.id), " +
            "COUNT(CASE WHEN sah.paid IS NOT NULL THEN TRUE END), " +
            "COUNT(CASE WHEN (sah.quota IS NOT NULL OR sah.quota <> 'NONE') THEN TRUE END)) " +
            "FROM StudentApplicationHistory sah " +
            "INNER JOIN sah.unit u " +
            "WHERE sah.university = :university " +
            "GROUP BY u.id")
    List<AdminDashboardDto> findUniversityStatus(@Param("university") University university);

    @EntityGraph(value = "StudentApplicationHistory.detail", type = EntityGraph.EntityGraphType.LOAD)
    List<StudentApplicationHistory> findAllByUniversityAndUnit(University university, Unit unit);

    @Query("select h from StudentApplicationHistory h join h.studentInfo s join h.unit where s.userName = :userName")
    List<StudentApplicationHistory> loadHistoryByUserName(@Param("userName") String userName);

    @Query(value = "SELECT si.group, COUNT(si.group) " +
            "FROM StudentApplicationHistory sah " +
            "INNER JOIN sah.studentInfo si " +
            "WHERE sah.university = :university AND sah.unit = :unit " +
            "GROUP BY si.group " +
            "ORDER BY si.group")
    List<Object[]> findGroupStatusByUnit(@Param("university") University university, @Param("unit") Unit unit);

    @Query(value = "SELECT si.hscBoard, COUNT(si.hscBoard) " +
            "FROM StudentApplicationHistory sah " +
            "INNER JOIN sah.studentInfo si " +
            "WHERE sah.university = :university AND sah.unit = :unit " +
            "GROUP BY si.hscBoard " +
            "ORDER BY si.hscBoard ")
    List<Object[]> findBoardStatusByUnit(@Param("university") University university, @Param("unit") Unit unit);
}
