package com.progoti.surecash.admission.dao;

import com.progoti.surecash.admission.domain.AdmissionSession;
import com.progoti.surecash.admission.domain.Unit;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.repository.AdmissionSessionRepository;
import com.progoti.surecash.admission.repository.UnitRepository;
import com.progoti.surecash.admission.repository.UniversityRepository;
import com.progoti.surecash.admission.request.AcademicInformationRequest;
import com.progoti.surecash.admission.response.UnitInfo;
import com.progoti.surecash.admission.response.StudentInfoResponse;
import com.progoti.surecash.admission.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shaown on 1:27 PM.
 */
@Repository
public class AcademicDaoImpl implements AcademicDao {
    @Value("${hsc.table.name}")
    String hscTable;
    @Value("${ssc.table.name}")
    String sscTable;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private AdmissionSessionRepository admissionSessionRepository;
    @Autowired
    private UniversityRepository universityRepository;

    @Override
    public StudentInfoResponse getStudentInfo(AcademicInformationRequest request) {
        String query = "SELECT hsc.name, hsc.fname, hsc.mname, hsc.hsc_group, hsc.gpa AS hsc_gpa, ssc.ssc_group, ssc.gpa AS ssc_gpa " +
                "FROM " + hscTable + " AS hsc " +
                "INNER JOIN " + sscTable + " AS ssc " +
                "ON hsc.ssc_board = ssc.board_name AND hsc.ssc_passyr = ssc.pass_year AND hsc.ssc_rollno = ssc.roll_no " +
                "WHERE hsc.board_name = ? AND hsc.roll_no = ? AND hsc.regno = ? AND hsc.pass_year = ? " +
                "AND ssc.regno = ? AND ssc.roll_no = ? AND ssc.pass_year = ? AND ssc.board_name = ?";

        Object[] params = {request.getHscInformation().getBoard().name().toUpperCase(), request.getHscInformation().getRoll(), request.getHscInformation().getRegNo(), request.getHscInformation().getPassingYear(),
        request.getSscInformation().getRegNo(), request.getSscInformation().getRoll(), request.getSscInformation().getPassingYear(), request.getSscInformation().getBoard().name().toUpperCase()};
        int[] argTypes = {Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR};

        List<StudentInfoResponse> studentInfoResponseList = jdbcTemplate.query(query, params, argTypes, new RowMapper<StudentInfoResponse>() {
            @Override
            public StudentInfoResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                StudentInfoResponse studentInfoResponse = new StudentInfoResponse();
                studentInfoResponse.setName(rs.getString("name"));
                studentInfoResponse.setFatherName(rs.getString("fname"));
                studentInfoResponse.setMotherName(rs.getString("mname"));
                studentInfoResponse.setHscInfo(request.getHscInformation());
                studentInfoResponse.getHscInfo().setGroup(Constants.Group.valueOf(rs.getString("hsc_group")));
                studentInfoResponse.getHscInfo().setGpa(rs.getDouble("hsc_gpa"));
                studentInfoResponse.setSscInfo(request.getSscInformation());
                studentInfoResponse.getSscInfo().setGroup(Constants.Group.valueOf(rs.getString("ssc_group")));
                studentInfoResponse.getSscInfo().setGpa(rs.getDouble("ssc_gpa"));
                studentInfoResponse.setUnitInfo(getUnitInfoListFromUniversityAndSession(universityRepository.findOne(1), Constants.AdmissionSession.SESSION_2017_2018));

                return studentInfoResponse;
            }
        });

        return studentInfoResponseList.get(0);
    }

    @Override
    public List<UnitInfo> getUnitInfoListFromUniversityAndSession(University university,
            Constants.AdmissionSession session) {
        List<Unit> unitList = unitRepository.findAllByUniversity(university);

        List<AdmissionSession> admissionSessionList = admissionSessionRepository
                .findAllBySessionAndUnitIn(session.value, unitList);
        Map<Integer, Double> unitPrice = new HashMap<>();
        for (AdmissionSession admissionSession : admissionSessionList) {
            unitPrice.put(admissionSession.getUnit().getId(), admissionSession.getFormPrice());
        }

        List<UnitInfo> unitInfoList = new ArrayList<>();

        for (Unit unit : unitList) {
            String fees = Constants.DECIMAL_FORMAT.format(unitPrice.get(unit.getId()));
            unitInfoList.add(new UnitInfo(unit.getId(), unit.getCode(), unit.getName(), fees));
        }
        return unitInfoList;
    }
}
