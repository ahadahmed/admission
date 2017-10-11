package com.progoti.surecash.admission.service;

import com.progoti.surecash.admission.dao.AcademicDao;
import com.progoti.surecash.admission.domain.*;
import com.progoti.surecash.admission.repository.*;
import com.progoti.surecash.admission.request.AcademicInformationRequest;
import com.progoti.surecash.admission.request.ApplicationFormRequest;
import com.progoti.surecash.admission.request.ReconcileRequest;
import com.progoti.surecash.admission.response.CredentialResponse;
import com.progoti.surecash.admission.response.PaymentResponse;
import com.progoti.surecash.admission.response.ProfileResponse;
import com.progoti.surecash.admission.response.StudentInfoResponse;
import com.progoti.surecash.admission.utility.Constants;
import com.progoti.surecash.admission.utility.RestClient;
import com.progoti.surecash.dto.SwitchPaymentRequestDto;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

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
    private AdmissionPaymentRequestRepository admissionPaymentRequestRepository;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;


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
        profileResponse.setBase64Image();
        profileResponse.setName(studentInfoResponse.getName());
        profileResponse.setFatherName(studentInfoResponse.getFatherName());
        profileResponse.setMotherName(studentInfoResponse.getMotherName());

        return profileResponse;
    }

    @Override
    public PaymentResponse doPayment(AdmissionPaymentRequest paymentRequest) throws IOException, URISyntaxException {
        PaymentResponse paymentResponse = new PaymentResponse();
        University university = universityRepository.findOneByBillerCode(paymentRequest.getBillerCode());

        // if institution not found

        if(university == null){
            paymentResponse.setStatus(Constants.Transaction_Status.ERROR.name());
            paymentResponse.setDescription(Constants.ErrorMessage.INSTITUTION_NOT_FOUND.value);
            System.out.println(Constants.ErrorMessage.INSTITUTION_NOT_FOUND.value);
            return paymentResponse;
        }

        StudentApplicationHistory applicationHistory = studentApplicationHistoryRepository.findOneByApplicationIdAndUniversity(paymentRequest.getApplicantId(), university);

        // if applicant already paid

        if(applicationHistory.getPaid() != null && applicationHistory.getTranxId() != null){
            paymentResponse.setStatus(Constants.Transaction_Status.ERROR.name());
            paymentResponse.setDescription(Constants.ErrorMessage.NO_DUE.value);
            System.out.println(Constants.ErrorMessage.NO_DUE.value);
            return paymentResponse;
        }

        // if applicant amount is equal to due amount

        if(paymentRequest.getAmount().equals(applicationHistory.getPayableAmount())){
            paymentRequest.setToWallet(university.getWallet());
            AdmissionPaymentRequest request = admissionPaymentRequestRepository.saveAndFlush(paymentRequest);

            // switch doPayment calling
            Map<String, String> responseMap = doSwitchCallForPayment(request, applicationHistory);

            paymentResponse = new PaymentResponse(responseMap);
        } else {
            paymentResponse.setStatus(Constants.Transaction_Status.ERROR.name());
            String errorDescription = Constants.ErrorMessage.INVALID_AMOUNT.value.replace("?", String.valueOf(applicationHistory.getPayableAmount()));
            System.out.println(errorDescription);
            paymentResponse.setDescription(errorDescription);
        }
        return paymentResponse;
    }

    @Override
    public PaymentResponse reconcilePayment(ReconcileRequest reconcileRequest) {
        PaymentResponse paymentResponse = new PaymentResponse();
        University university = universityRepository.findOneByBillerCode(reconcileRequest.getBillerCode());

        // if institution not found

        if(university == null){
            paymentResponse.setStatus(Constants.Transaction_Status.ERROR.name());
            paymentResponse.setDescription(Constants.ErrorMessage.INSTITUTION_NOT_FOUND.value);
            System.out.println(Constants.ErrorMessage.INSTITUTION_NOT_FOUND.value);
            return paymentResponse;
        }

        StudentApplicationHistory applicationHistory = studentApplicationHistoryRepository.findOneByApplicationIdAndUniversity(reconcileRequest.getApplicantId(), university);

        // if applicant already paid

        if(applicationHistory.getPaid() != null && applicationHistory.getTranxId() != null){
            paymentResponse.setStatus(Constants.Transaction_Status.ERROR.name());
            paymentResponse.setDescription(Constants.ErrorMessage.NO_DUE.value);
            System.out.println(Constants.ErrorMessage.NO_DUE.value);
            return paymentResponse;
        }

        // if applicant amount is equal to due amount

        if(reconcileRequest.getAmount().equals(applicationHistory.getPayableAmount())){
            reconcileRequest.setToWallet(university.getWallet());
            // must set remark to identify it's a reconcile request
            reconcileRequest.setRemark(Constants.Transaction_Status.RECONCILE.name());
            admissionPaymentRequestRepository.saveAndFlush(reconcileRequest);

            Map<String, String> responseMap = doReconcileForPayment(reconcileRequest, applicationHistory);
            paymentResponse = new PaymentResponse(responseMap);
        } else {
            paymentResponse.setStatus(Constants.Transaction_Status.ERROR.name());
            String errorDescription = Constants.ErrorMessage.INVALID_AMOUNT.value.replace("?", String.valueOf(applicationHistory.getPayableAmount()));
            System.out.println(errorDescription);
            paymentResponse.setDescription(errorDescription);
        }
        return paymentResponse;
    }

    @Transactional
    private Map<String, String> doReconcileForPayment(ReconcileRequest request, StudentApplicationHistory applicationHistory){
        TransactionHistory transactionHistory = transactionHistoryRepository.findOneByAdmissionPaymentRequest(request);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("status", Constants.Transaction_Status.PROCESSED.name());
        responseMap.put("description", "Transaction Successful");
        responseMap.put("trnxId", request.getProfinoTransactionId());

        // update in transaction history table
        transactionHistory.setStatus(responseMap.get("status"));
        transactionHistory.setTrnxId(responseMap.get("trnxId"));
        transactionHistory.setUpdateDate(new Date());
        transactionHistoryRepository.save(transactionHistory);

        // update in student application history table
        applicationHistory.setPaid(Boolean.TRUE);
        applicationHistory.setTranxId(transactionHistory.getTrnxId());
        applicationHistory.setUpdateDate(new Date());
        studentApplicationHistoryRepository.saveAndFlush(applicationHistory);

        return responseMap;
    }

    @Transactional
    private Map<String, String> doSwitchCallForPayment(AdmissionPaymentRequest request, StudentApplicationHistory applicationHistory) throws IOException, URISyntaxException {
        TransactionHistory transactionHistory = transactionHistoryRepository.findOneByAdmissionPaymentRequest(request);

        SwitchPaymentRequestDto switchPaymentRequestDto = new SwitchPaymentRequestDto();
        switchPaymentRequestDto.doReflectionUsingAdmissionPaymentRequest(request);

        HttpResponse response = RestClient.doSwitchPaymentRequest(switchPaymentRequestDto);
        Map<String, String> responseMap = RestClient.parseResponseText(response);

        // update in transaction history table
        transactionHistory.setStatus(responseMap.get("status"));
        transactionHistory.setTrnxId(responseMap.get("trnxId"));
        transactionHistory.setUpdateDate(new Date());
        transactionHistoryRepository.save(transactionHistory);

        // update in student application history table
        applicationHistory.setPaid(Boolean.TRUE);
        applicationHistory.setTranxId(transactionHistory.getTrnxId());
        applicationHistory.setUpdateDate(new Date());
        studentApplicationHistoryRepository.saveAndFlush(applicationHistory);

        return responseMap;
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
        studentInfo.setGroup(request.getHscInfo().getGroup());
    }
}
