package com.progoti.surecash.controller;

import com.progoti.surecash.admission.dao.AcademicDao;
import com.progoti.surecash.admission.domain.AdmissionPaymentRequest;
import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.repository.StudentInfoRepository;
import com.progoti.surecash.admission.repository.UniversityRepository;
import com.progoti.surecash.admission.request.AcademicInformationRequest;
import com.progoti.surecash.admission.request.ApplicationFormRequest;
import com.progoti.surecash.admission.request.ProfileUpdateRequest;
import com.progoti.surecash.admission.request.ReconcileRequest;
import com.progoti.surecash.admission.response.CredentialResponse;
import com.progoti.surecash.admission.response.ErrorResponse;
import com.progoti.surecash.admission.response.PaymentResponse;
import com.progoti.surecash.admission.response.StudentInfoResponse;
import com.progoti.surecash.admission.service.AdmissionService;
import com.progoti.surecash.admission.utility.Constants;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

/**
 * Created by Shaown on 12:07 PM.
 */
@RestController
@RequestMapping("academic")
public class AcademicInfoController {
    @Autowired
    private AcademicDao academicDao;
    @Autowired
    private AdmissionService admissionService;
    @Autowired
    private StudentInfoRepository studentInfoRepository;
    @Autowired
    private UniversityRepository universityRepository;


    @RequestMapping(value = "validate-info", method = RequestMethod.POST)
    public StudentInfoResponse validateAcademicInfo(@RequestBody @Valid AcademicInformationRequest request){
        return academicDao.getStudentInfo(request);
    }

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public CredentialResponse submitApplicationForm(@RequestBody @Valid ApplicationFormRequest request){
        //TODO: need to set university id
        request.setUniversityId(1);
        return admissionService.submitForm(request);
    }

    @RequestMapping(value = "profile-update", method = RequestMethod.POST)
    public String uploadImageFile(@RequestParam(value = "image-file", required = false) MultipartFile imageFile,
                                  @RequestParam(value = "email", required = false) String email ) throws IOException {
        //TODO: need to change the static student id
        StudentInfo studentInfo = studentInfoRepository.getOne(7);
        studentInfo.setEmail(email);
        if(imageFile != null && imageFile.getSize() > 0){
            studentInfo.setImage(imageFile.getBytes());
        }
        studentInfo.setUpdateDate(new Date());
        studentInfoRepository.saveAndFlush(studentInfo);
        return "SUCCESS";
    }



    @RequestMapping(value = "admin/update/profile", method = RequestMethod.POST)
    public String updateProfile(@RequestParam(value = "image-file", required = false) MultipartFile imageFile,
                                @RequestParam(value = "email", required = true)@Valid @Email String email,
                                @RequestParam(value = "contactNo", required = true) String contact,
                                @RequestParam(value = "address", required = true) String address) throws IOException {
        //TODO: need to change in university static value
        University university = universityRepository.findOne(1);
        ProfileUpdateRequest updateRequest = new ProfileUpdateRequest(imageFile, email, address, contact);
        updateRequest.setNonNullValueForUniversityUpdate(university);
        universityRepository.saveAndFlush(university);
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
