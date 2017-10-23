package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.StudentApplicationHistory;
import com.progoti.surecash.admission.domain.StudentInfo;
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
    @Query(value = "select sah from StudentApplicationHistory sah join sah.unit u where sah.applicationId = :applicantId and u.university = :university")
    StudentApplicationHistory findOneByApplicationIdAndUniversity(@Param("applicantId")String applicantId, @Param("university")University university);

    @Query(value = "SELECT NEW com.progoti.surecash.dto.AdminDashboardDto(u.id, u.name, u.code, COUNT(sah.id), " +
            "COUNT(CASE WHEN sah.paid IS NOT NULL THEN TRUE END), " +
            "COUNT(CASE WHEN (sah.quota IS NOT NULL OR sah.quota <> 'NONE') THEN TRUE END)) " +
            "FROM StudentApplicationHistory sah " +
            "INNER JOIN sah.unit u " +
            "WHERE sah.active = TRUE AND u.university = :university " +
            "GROUP BY u.id")
    List<AdminDashboardDto> findUniversityStatus(@Param("university") University university);

    @Query(value = "select sah from StudentApplicationHistory sah join fetch sah.studentInfo where sah.unit = :unit and sah.unit.university = :university and sah.active = :isActive")
    List<StudentApplicationHistory> findAllByUniversityAndUnitAndActive(@Param("university") University university, @Param("unit") Unit unit, @Param("isActive") Boolean isActive);

    @Query(value = "select sah from StudentApplicationHistory sah join sah.unit u where u.university = :university and sah.studentInfo = :studentInfo")
    List<StudentApplicationHistory> findAllByStudentInfoAndUniversity(@Param("studentInfo")StudentInfo studentInfo, @Param("university")University university);

    @Query(value = "SELECT si.hscGroup, COUNT(si.hscGroup) " +
            "FROM StudentApplicationHistory sah " +
            "JOIN sah.studentInfo si " +
            "JOIN sah.unit u " +
            "WHERE sah.unit = :unit AND u.university = :university AND sah.active = TRUE " +
            "GROUP BY si.hscGroup " +
            "ORDER BY si.hscGroup")
    List<Object[]> findGroupStatusByUnit(@Param("university") University university, @Param("unit") Unit unit);

    @Query(value = "SELECT si.hscBoard, COUNT(si.hscBoard) " +
            "FROM StudentApplicationHistory sah " +
            "JOIN sah.unit u " +
            "JOIN sah.studentInfo si " +
            "WHERE sah.unit = :unit AND u.university = :university AND sah.active = TRUE " +
            "GROUP BY si.hscBoard " +
            "ORDER BY si.hscBoard ")
    List<Object[]> findBoardStatusByUnit(@Param("university") University university, @Param("unit") Unit unit);

    List<StudentApplicationHistory> findAllByStudentInfoAndActive(StudentInfo studentInfo, Boolean isActive);
}
