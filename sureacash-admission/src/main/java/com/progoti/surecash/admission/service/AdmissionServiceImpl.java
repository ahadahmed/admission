package com.progoti.surecash.admission.service;

import com.progoti.surecash.admission.dao.AcademicDao;
import com.progoti.surecash.admission.domain.StudentApplicationHistory;
import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.repository.*;
import com.progoti.surecash.admission.request.AcademicInformationRequest;
import com.progoti.surecash.admission.request.ApplicationFormRequest;
import com.progoti.surecash.admission.response.CredentialResponse;
import com.progoti.surecash.admission.response.ProfileResponse;
import com.progoti.surecash.admission.response.StudentInfoResponse;
import com.progoti.surecash.admission.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaown on 3:14 PM.
 */
@Service
public class AdmissionServiceImpl implements AdmissionService {
    @Autowired
    private StudentInfoRepository studentInfoRepository;
    @Autowired
    private StudentApplicationHistoryRepository studentApplicationHistoryRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private AdmissionSessionRepository admissionSessionRepository;
    @Autowired
    private AcademicDao academicDao;
    @Autowired
    private UniversityRepository universityRepository;



    @Override
    @Transactional
    public CredentialResponse submitForm(ApplicationFormRequest request) {
        StudentInfo studentInfo = new StudentInfo();
        doStudentInfoReflection(studentInfo, request);
        studentInfo.setUserName(Constants.userNameGenerator(request.getUniversityId()));
        studentInfo.setUniversity(universityRepository.getOne(request.getUniversityId()));
        StudentInfo student = studentInfoRepository.save(studentInfo);

        List<StudentApplicationHistory> studentApplicationHistoryList = new ArrayList<>();
        doStudentApplicationHistoryReflection(studentApplicationHistoryList, request, student);

        studentApplicationHistoryRepository.save(studentApplicationHistoryList);
        return new CredentialResponse(studentInfo.getUserName(), request.getPassword());
    }

    @Override
    public ProfileResponse getStudentProfile(StudentInfo studentInfo) {

        AcademicInformationRequest request = new AcademicInformationRequest();
        request.doReflectionFromStudentInfo(studentInfo);

        StudentInfoResponse studentInfoResponse = academicDao.getStudentInfo(request);

        ProfileResponse profileResponse = new ProfileResponse(null, studentInfo.getEmail(), studentInfo.getMobile());
        profileResponse.setImageData(studentInfo.getImage());
        profileResponse.setName(studentInfoResponse.getName());
        profileResponse.setFatherName(studentInfoResponse.getFatherName());
        profileResponse.setMotherName(studentInfoResponse.getMotherName());

        return profileResponse;
    }

    @Transactional
    private void doStudentApplicationHistoryReflection(List<StudentApplicationHistory> studentApplicationHistoryList, ApplicationFormRequest request, StudentInfo studentInfo) {
        for(Integer unitId : request.getUnitList()){
            StudentApplicationHistory applicationHistory = new StudentApplicationHistory();
            applicationHistory.setUnit(unitRepository.getOne(unitId));
            applicationHistory.setStudentInfo(studentInfo);
            applicationHistory.setPayableAmount(admissionSessionRepository.findOneByUnit(unitRepository.getOne(unitId)).getFormPrice());
            applicationHistory.setApplicationId(String.valueOf(Constants.applicationIdGenerator(unitId)));
            applicationHistory.setUniversity(universityRepository.getOne(request.getUniversityId()));
            applicationHistory.setQuota(request.getQuota());
            applicationHistory.setActive(Boolean.TRUE);
            studentApplicationHistoryList.add(applicationHistory);
        }
    }

    @Transactional
    private void doStudentInfoReflection(StudentInfo studentInfo, ApplicationFormRequest request) {
        studentInfo.setPassword(request.getPassword());
        studentInfo.setName(request.getName());
        studentInfo.setSscRoll(String.valueOf(request.getSscInfo().getRoll()));
        studentInfo.setHscRoll(String.valueOf(request.getHscInfo().getRoll()));
        studentInfo.setSscReg(String.valueOf(request.getSscInfo().getRegNo()));
        studentInfo.setHscReg(String.valueOf(request.getHscInfo().getRegNo()));
        studentInfo.setSscGPA(request.getSscInfo().getGpa());
        studentInfo.setHscGPA(request.getHscInfo().getGpa());
        studentInfo.setMobile(request.getMobile());
        studentInfo.setEmail(request.getEmail());
        studentInfo.setSscPassingYear(request.getSscInfo().getPassingYear());
        studentInfo.setHscPassingYear(request.getHscInfo().getPassingYear());
        studentInfo.setSscBoard(request.getSscInfo().getBoard());
        studentInfo.setHscBoard(request.getHscInfo().getBoard());
        studentInfo.setSscGroup(request.getSscInfo().getGroup());
        studentInfo.setHscGroup(request.getHscInfo().getGroup());
    }
}
