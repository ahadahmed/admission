package com.progoti.surecash.controller;

import com.progoti.surecash.admission.domain.StudentApplicationHistory;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.repository.StudentApplicationHistoryRepository;
import com.progoti.surecash.admission.repository.UnitRepository;
import com.progoti.surecash.admission.repository.UniversityRepository;
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

    @GetMapping(value = "/dashboard")
    public String getAdminDashboard(Model model) {
        //TODO: need to change this static value
        University university = universityRepository.getOne(1);
        List<AdminDashboardDto> universityStatus = studentApplicationHistoryRepository.findUniversityStatus(university);
        int totalApplicant = 0, totalPaid = 0;
        for (AdminDashboardDto dto : universityStatus) {
            totalApplicant += dto.getApplicantCount();
            totalPaid += dto.getPaidApplicant();
        }
        model.addAttribute("statusList", universityStatus);
        model.addAttribute("unit", universityStatus.size());
        model.addAttribute("totalApplicant", totalApplicant);
        model.addAttribute("totalPaidApplicant", totalPaid);
        return "admin/dashboard";

    }

    @GetMapping(value = "/unit-details")
    public String getAdminSearch(Model model, @RequestParam(value = "unitId", required = false) Integer unitId) {
        if (unitId != null) {
            List<StudentApplicationHistory> applicantList = studentApplicationHistoryRepository.findAllByUniversityAndUnit(universityRepository.getOne(1), unitRepository.getOne(unitId));
            model.addAttribute("applicantList", applicantList);
        }
        return "admin/unit_details";

    }

    @GetMapping(value = "/applicant-details")
    public String getApplicantDetails(Model model){
        return "admin/applicant_details";
    }

    @GetMapping(value = "/university-details")
    public String getUniversityDetails(Model model){
        return "admin/university_details";
    }

    @GetMapping(value = "/show-university-profile")
    public String showUniversityProfile(Model model){
        return "admin/edit_university_profile";
    }
}
