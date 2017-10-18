package com.progoti.surecash.admission.service;

import com.progoti.surecash.admission.dao.AcademicDao;
import com.progoti.surecash.admission.domain.StudentApplicationHistory;
import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.domain.User;
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
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;



    @Override
    @Transactional
    public CredentialResponse submitForm(ApplicationFormRequest request) {
        University university = universityRepository.getOne(request.getUniversityId());

        StudentInfo studentInfo = new StudentInfo();
        doStudentInfoReflection(studentInfo, request);
        studentInfo.setUniversity(university);
        StudentInfo student = studentInfoRepository.save(studentInfo);

        User user = new User();
        user.setUniv(university);
        doUserInfoReflection(user, request, student);
        userRepository.saveAndFlush(user);

        List<StudentApplicationHistory> studentApplicationHistoryList = new ArrayList<>();
        doStudentApplicationHistoryReflection(studentApplicationHistoryList, request, student);
        studentApplicationHistoryRepository.save(studentApplicationHistoryList);

        return new CredentialResponse(user.getUserName(), user.getPassword());
    }

    @Transactional
    private void doUserInfoReflection(User user, ApplicationFormRequest formRequest, StudentInfo studentInfo) {
        user.setEmail(formRequest.getEmail());
        user.setMobile(formRequest.getMobile());
        user.setStudent(true);
        user.setStudentId(studentInfo);
        user.setUserName(Constants.userNameGenerator(formRequest.getUniversityId()));
        user.setPassword(formRequest.getPassword());
        user.setRole(roleRepository.findOneByRoleName(Constants.RoleName.USER));
    }

    @Override
    public ProfileResponse getStudentProfile(StudentInfo studentInfo) {

        AcademicInformationRequest request = new AcademicInformationRequest();
        request.doReflectionFromStudentInfo(studentInfo);

        StudentInfoResponse studentInfoResponse = academicDao.getStudentInfo(request);
        User user = userRepository.findOneByStudentId(studentInfo);

        ProfileResponse profileResponse = new ProfileResponse(null, user.getEmail(), user.getMobile());
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
        studentInfo.setName(request.getName());
        studentInfo.setSscRoll(String.valueOf(request.getSscInfo().getRoll()));
        studentInfo.setHscRoll(String.valueOf(request.getHscInfo().getRoll()));
        studentInfo.setSscReg(String.valueOf(request.getSscInfo().getRegNo()));
        studentInfo.setHscReg(String.valueOf(request.getHscInfo().getRegNo()));
        studentInfo.setSscGPA(request.getSscInfo().getGpa());
        studentInfo.setHscGPA(request.getHscInfo().getGpa());
        studentInfo.setSscPassingYear(request.getSscInfo().getPassingYear());
        studentInfo.setHscPassingYear(request.getHscInfo().getPassingYear());
        studentInfo.setSscBoard(request.getSscInfo().getBoard());
        studentInfo.setHscBoard(request.getHscInfo().getBoard());
        studentInfo.setSscGroup(request.getSscInfo().getGroup());
        studentInfo.setHscGroup(request.getHscInfo().getGroup());
    }
}
