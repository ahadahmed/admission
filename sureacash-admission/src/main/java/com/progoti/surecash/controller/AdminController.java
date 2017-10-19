package com.progoti.surecash.controller;

import com.progoti.surecash.admission.domain.StudentApplicationHistory;
import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.domain.Unit;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.repository.*;
import com.progoti.surecash.admission.request.ProfileUpdateRequest;
import com.progoti.surecash.admission.service.AdmissionService;
import com.progoti.surecash.admission.utility.SecurityUtils;
import com.progoti.surecash.dto.AdminDashboardDto;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by Shaown on 5:36 PM.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private StudentApplicationHistoryRepository studentApplicationHistoryRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private StudentInfoRepository studentInfoRepository;
    @Autowired
    private AdmissionService admissionService;
    @Autowired
    private AdmissionSessionRepository admissionSessionRepository;
    @Autowired
    private UniversityRepository universityRepository;

    @GetMapping(value = "/dashboard")
    public String getAdminDashboard(Model model, HttpServletRequest servletRequest) {
        University university = (University) servletRequest.getServletContext().getAttribute(servletRequest.getServerName());
        List<AdminDashboardDto> universityStatusList = studentApplicationHistoryRepository.findUniversityStatus(university);
        int totalApplicant = 0, totalPaid = 0, totalQuotaApplicant = 0;
        for (AdminDashboardDto dto : universityStatusList) {
            Unit unit = unitRepository.getOne(dto.getUnitId());
            dto.setBoardStatusList(studentApplicationHistoryRepository.findBoardStatusByUnit(university, unit));
            dto.setGroupStatusList(studentApplicationHistoryRepository.findGroupStatusByUnit(university, unit));

            totalApplicant += dto.getApplicantCount();
            totalPaid += dto.getPaidApplicant();
            totalQuotaApplicant += dto.getQuotaApplicant();

        }
        model.addAttribute("university", university);
        model.addAttribute("statusList", universityStatusList);
        model.addAttribute("unit", universityStatusList.size());
        model.addAttribute("totalApplicant", totalApplicant);
        model.addAttribute("totalPaidApplicant", totalPaid);
        model.addAttribute("totalQuotaApplicant", totalQuotaApplicant);

        return "admin/dashboard";

    }

    @GetMapping(value = "/unit-details")
    public String getAdminSearch(Model model, @RequestParam(value = "unitId", required = true) Integer unitId, HttpServletRequest servletRequest) {
        University university = (University) servletRequest.getServletContext().getAttribute(servletRequest.getServerName());
        model.addAttribute("university", university);
        List<StudentApplicationHistory> applicantList = studentApplicationHistoryRepository.findAllByUniversityAndUnitAndActive(university, unitRepository.getOne(unitId), Boolean.TRUE);
        model.addAttribute("applicantList", applicantList);
        return "admin/unit_details";

    }

    @GetMapping(value = "/applicant-details")
    public String getApplicantDetails(Model model, @RequestParam(value = "studentId", required = true) Integer studentId, HttpServletRequest servletRequest){
        University university = (University) servletRequest.getServletContext().getAttribute(servletRequest.getServerName());
        model.addAttribute("university", university);
        StudentInfo studentInfo = studentInfoRepository.getOne(studentId);
        model.addAttribute("student", studentInfo);
        model.addAttribute("profile", admissionService.getStudentProfile(studentInfo));
        model.addAttribute("applicationList", studentApplicationHistoryRepository.findAllByStudentInfoAndActive(studentInfo, Boolean.TRUE));
        return "admin/applicant_details";
    }

    @GetMapping(value = "/university-details")
    public String getUniversityDetails(Model model, HttpServletRequest servletRequest){
        University university = (University) servletRequest.getServletContext().getAttribute(servletRequest.getServerName());
        model.addAttribute("university", university);
        model.addAttribute("admissionSessionList", admissionSessionRepository.findAllUnitByUniversity(university));
        return "admin/university_details";
    }

    @GetMapping(value = "/show-university-profile")
    public String showUniversityProfile(Model model){
        model.addAttribute("university", SecurityUtils.getUserDetails().getUser().getUniv());
        return "admin/edit_university_profile";
    }

    @RequestMapping(value = "/update/profile", method = RequestMethod.POST)
    public String updateProfile(@RequestParam(value = "image-file", required = false) MultipartFile imageFile,
                                @RequestParam(value = "email", required = true)@Valid @Email String email,
                                @RequestParam(value = "contactNo", required = true) String contact,
                                @RequestParam(value = "address", required = true) String address, HttpServletRequest servletRequest) throws IOException {
        University university = (University) servletRequest.getServletContext().getAttribute(servletRequest.getServerName());
        ProfileUpdateRequest updateRequest = new ProfileUpdateRequest(imageFile, email, address, contact);
        updateRequest.setNonNullValueForUniversityUpdate(university);
        universityRepository.saveAndFlush(university);
        return "SUCCESS";
    }
}
