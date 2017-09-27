package com.progoti.surecash.controller;

import com.progoti.surecash.admission.repository.AcademicRepository;
import com.progoti.surecash.admission.request.AcademicInformationRequest;
import com.progoti.surecash.admission.response.ErrorResponse;
import com.progoti.surecash.admission.response.StudentInfoResponse;
import com.progoti.surecash.admission.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Shaown on 12:07 PM.
 */
@Controller
@RequestMapping("academic")
public class AcademicInfoController {
    @Autowired
    private AcademicRepository academicRepository;

    @RequestMapping(value = "validate-info", method = RequestMethod.POST)
    public String validateAcademicInfo(@RequestBody @Valid AcademicInformationRequest request,Model model){
        model.addAttribute("studentInfo", academicRepository.getStudentInfo(request));
        System.out.println(academicRepository.getStudentInfo(request));
        return "unit_list";
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
