package com.progoti.surecash.controller;

import com.progoti.surecash.admission.dao.AcademicDao;
import com.progoti.surecash.admission.domain.Enquiry;
import com.progoti.surecash.admission.repository.EnquiryRepository;
import com.progoti.surecash.admission.request.AcademicInformationRequest;
import com.progoti.surecash.admission.request.ApplicationFormRequest;
import com.progoti.surecash.admission.response.CredentialResponse;
import com.progoti.surecash.admission.response.ErrorResponse;
import com.progoti.surecash.admission.response.StudentInfoResponse;
import com.progoti.surecash.admission.service.FormSubmitService;
import com.progoti.surecash.admission.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @RequestMapping(value = "validate-info", method = RequestMethod.POST)
    public StudentInfoResponse validateAcademicInfo(@RequestBody @Valid AcademicInformationRequest request){
        return academicDao.getStudentInfo(request);
    }

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public CredentialResponse submitApplicationForm(@RequestBody @Valid ApplicationFormRequest request){
        return formSubmitService.submitForm(request);
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
