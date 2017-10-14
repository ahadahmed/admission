package com.progoti.surecash.controller;

import com.progoti.surecash.admission.domain.StudentApplicationHistory;
import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.domain.Unit;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.repository.*;
import com.progoti.surecash.admission.service.AdmissionService;
import com.progoti.surecash.dto.AdminDashboardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Shaown on 5:36 PM.
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private StudentApplicationHistoryRepository studentApplicationHistoryRepository;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private StudentInfoRepository studentInfoRepository;
    @Autowired
    private AdmissionService admissionService;
    @Autowired
    private AdmissionSessionRepository admissionSessionRepository;

    @GetMapping(value = "/dashboard")
    public String getAdminDashboard(Model model) {
        //TODO: need to change this static value
        University university = universityRepository.getOne(1);
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
    public String getAdminSearch(Model model, @RequestParam(value = "unitId", required = true) Integer unitId) {
        //TODO: need to change this static value
        model.addAttribute("university", universityRepository.getOne(1));
        List<StudentApplicationHistory> applicantList = studentApplicationHistoryRepository.findAllByUniversityAndUnit(universityRepository.getOne(1), unitRepository.getOne(unitId));
        model.addAttribute("applicantList", applicantList);
        return "admin/unit_details";

    }

    @GetMapping(value = "/applicant-details")
    public String getApplicantDetails(Model model, @RequestParam(value = "studentId", required = true) Integer studentId){
        //TODO: need to change this static value
        model.addAttribute("university", universityRepository.getOne(1));
        StudentInfo studentInfo = studentInfoRepository.getOne(studentId);
        model.addAttribute("student", studentInfo);
        model.addAttribute("profile", admissionService.getStudentProfile(studentInfo));
        model.addAttribute("applicationList", studentApplicationHistoryRepository.findAllByStudentInfo(studentInfo));
        return "admin/applicant_details";
    }

    @GetMapping(value = "/university-details")
    public String getUniversityDetails(Model model){
        //TODO: need to change in university static value
        University university = universityRepository.findOne(1);
        model.addAttribute("university", university);
        model.addAttribute("admissionSessionList", admissionSessionRepository.findAllUnitByUniversity(university));
        return "admin/university_details";
    }

    @GetMapping(value = "/show-university-profile")
    public String showUniversityProfile(Model model){
        //TODO: need to change in university static value
        model.addAttribute("university", universityRepository.findOne(1));
        return "admin/edit_university_profile";
    }
}
