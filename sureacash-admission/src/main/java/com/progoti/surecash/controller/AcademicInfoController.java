package com.progoti.surecash.controller;

import com.progoti.surecash.admission.dao.AcademicDao;
import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.domain.User;
import com.progoti.surecash.admission.repository.StudentInfoRepository;
import com.progoti.surecash.admission.repository.UserRepository;
import com.progoti.surecash.admission.request.AcademicInformationRequest;
import com.progoti.surecash.admission.request.ApplicationFormRequest;
import com.progoti.surecash.admission.response.CredentialResponse;
import com.progoti.surecash.admission.response.ErrorResponse;
import com.progoti.surecash.admission.response.StudentInfoResponse;
import com.progoti.surecash.admission.service.AdmissionService;
import com.progoti.surecash.admission.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
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
    private UserRepository userRepository;


    @RequestMapping(value = "validate-info", method = RequestMethod.POST)
    public StudentInfoResponse validateAcademicInfo(@RequestBody @Valid AcademicInformationRequest request, HttpServletRequest servletRequest){
        University university = (University) servletRequest.getServletContext().getAttribute(servletRequest.getServerName());
        return academicDao.getStudentInfo(request, university);
    }

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public CredentialResponse submitApplicationForm(@RequestBody @Valid ApplicationFormRequest request, HttpServletRequest servletRequest){
        University university = (University) servletRequest.getServletContext().getAttribute(servletRequest.getServerName());
        request.setUniversityId(university.getId());
        return admissionService.submitForm(request);
    }

    @RequestMapping(value = "profile-update", method = RequestMethod.POST)
    public String uploadImageFile(@RequestParam(value = "image-file", required = false) MultipartFile imageFile,
                                  @RequestParam(value = "email", required = false) String email, HttpServletRequest servletRequest) throws IOException {
        University university = (University) servletRequest.getServletContext().getAttribute(servletRequest.getServerName());
        String userName = servletRequest.getUserPrincipal().getName();
        User user = userRepository.findOneByUserNameAndUniv(userName, university);
        user.setEmail(email);
        if(imageFile != null && imageFile.getSize() > 0){
            StudentInfo studentInfo = user.getStudentId();
            studentInfo.setImage(imageFile.getBytes());
            studentInfo.setUpdateDate(new Date());
            studentInfoRepository.saveAndFlush(studentInfo);
        }
        userRepository.saveAndFlush(user);
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
