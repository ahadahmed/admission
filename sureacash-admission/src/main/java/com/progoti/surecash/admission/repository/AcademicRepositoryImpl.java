package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.domain.AdmissionSession;
import com.progoti.surecash.admission.request.AcademicInformationRequest;
import com.progoti.surecash.admission.response.AdmissionInfo;
import com.progoti.surecash.admission.response.StudentInfoResponse;
import com.progoti.surecash.admission.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaown on 1:27 PM.
 */
@Repository
public class AcademicRepositoryImpl implements AcademicRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public StudentInfoResponse getStudentInfo(AcademicInformationRequest request) {
        String query = "SELECT hsc.name, hsc.fname, hsc.mname, hsc.hsc_group, hsc.gpa AS hsc_gpa, ssc.ssc_group, ssc.gpa AS ssc_gpa " +
                "FROM result_hsc_dhk_2015 AS hsc " +
                "INNER JOIN result_ssc_dhk_2013 AS ssc " +
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
                AdmissionInfo admissionInfo =new AdmissionInfo();
                admissionInfo.setUnitName("A");
                admissionInfo.setUnitDescription("Engineering");
                admissionInfo.setFees("500.00");
                admissionInfo.setId(1);
                studentInfoResponse.setName(rs.getString("name"));
                studentInfoResponse.setFatherName(rs.getString("fname"));
                studentInfoResponse.setMotherName(rs.getString("mname"));
                studentInfoResponse.setHscInfo(request.getHscInformation());
                studentInfoResponse.getHscInfo().setGroup(Constants.Group.valueOf(rs.getString("hsc_group")));
                studentInfoResponse.getHscInfo().setGpa(rs.getDouble("hsc_gpa"));
                studentInfoResponse.setSscInfo(request.getSscInformation());
                studentInfoResponse.getSscInfo().setGroup(Constants.Group.valueOf(rs.getString("ssc_group")));
                studentInfoResponse.getSscInfo().setGpa(rs.getDouble("ssc_gpa"));

                List<AdmissionInfo> admissionInfoList = new ArrayList<>();
                admissionInfoList.add(admissionInfo);
                studentInfoResponse.setAdmissionInfo(admissionInfoList);
                return studentInfoResponse;
            }
        });

        return studentInfoResponseList.get(0);
    }
}
