package com.progoti.surecash.controller;

import com.progoti.surecash.admission.dao.AcademicDao;
import com.progoti.surecash.admission.domain.AdmissionPaymentRequest;
import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.domain.TransactionHistory;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.repository.AdmissionPaymentRequestRepository;
import com.progoti.surecash.admission.repository.StudentInfoRepository;
import com.progoti.surecash.admission.repository.TransactionHistoryRepository;
import com.progoti.surecash.admission.repository.UniversityRepository;
import com.progoti.surecash.admission.request.AcademicInformationRequest;
import com.progoti.surecash.admission.request.ApplicationFormRequest;
import com.progoti.surecash.admission.response.CredentialResponse;
import com.progoti.surecash.admission.response.ErrorResponse;
import com.progoti.surecash.admission.response.StudentInfoResponse;
import com.progoti.surecash.admission.service.FormSubmitService;
import com.progoti.surecash.admission.utility.Constants;
import com.progoti.surecash.admission.utility.RestClient;
import com.progoti.surecash.dto.SwitchPaymentRequestDto;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

/**
 * Created by Shaown on 12:07 PM.
 */
@RestController
@RequestMapping("academic")
public class AcademicInfoController {
    @Autowired
    private AcademicDao academicDao;
    @Autowired
    private FormSubmitService formSubmitService;
    @Autowired
    private StudentInfoRepository studentInfoRepository;
    @Autowired
    private AdmissionPaymentRequestRepository admissionPaymentRequestRepository;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @RequestMapping(value = "validate-info", method = RequestMethod.POST)
    public StudentInfoResponse validateAcademicInfo(@RequestBody @Valid AcademicInformationRequest request){
        return academicDao.getStudentInfo(request);
    }

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public CredentialResponse submitApplicationForm(@RequestBody @Valid ApplicationFormRequest request){
        return formSubmitService.submitForm(request);
    }

    @RequestMapping(value = "profile-update", method = RequestMethod.POST)
    public String uploadImageFile(@RequestParam(value = "image-file", required = false) MultipartFile imageFile,
                                  @RequestParam(value = "email", required = false) String email ) throws IOException {
        //TODO: need to change the static student id
        StudentInfo studentInfo = studentInfoRepository.getOne(7);
        studentInfo.setEmail(email);
        if(imageFile != null){
            studentInfo.setImage(imageFile.getBytes());
        }
        studentInfoRepository.saveAndFlush(studentInfo);
        return "SUCCESS";
    }

    @RequestMapping(value = "doPayment", method = RequestMethod.POST)
    public String doPayment(@RequestBody AdmissionPaymentRequest paymentRequest) throws IOException, URISyntaxException {
        University university = universityRepository.findOneByBillerCode(paymentRequest.getBillerCode());
        paymentRequest.setToWallet(university.getContact());
        AdmissionPaymentRequest request = admissionPaymentRequestRepository.saveAndFlush(paymentRequest);
        TransactionHistory transactionHistory = transactionHistoryRepository.findOneByAdmissionPaymentRequest(request);

        SwitchPaymentRequestDto switchPaymentRequestDto = new SwitchPaymentRequestDto();
        switchPaymentRequestDto.doReflectionUsingAdmissionPaymentRequest(request);

        HttpResponse response = RestClient.doPostRequest(switchPaymentRequestDto);
        Map<String, String> responseMap = RestClient.parseResponseText(response);

        transactionHistory.setStatus(responseMap.get("status"));
        transactionHistory.setTrnxId(responseMap.get("trnxId"));
        transactionHistory.setUpdateDate(new Date());

        transactionHistoryRepository.save(transactionHistory);

        return "SUCCESS";
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleIndexOutOfBoundException(IndexOutOfBoundsException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(Constants.ErrorMessage.INVALID_ACADEMIC_INFO.value);
        errorResponse.setDescription(e.toString());
        return errorResponse;
    }
}
