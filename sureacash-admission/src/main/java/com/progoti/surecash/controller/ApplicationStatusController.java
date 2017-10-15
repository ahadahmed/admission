package com.progoti.surecash.controller;

import com.progoti.surecash.admission.service.ApplicationStatusService;
import com.progoti.surecash.admission.utility.AppProperties;
import com.progoti.surecash.admission.utility.SecurityUtils;
import com.progoti.surecash.dto.UnitDto;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/applicationStatus")
public class ApplicationStatusController {
    private static final Logger logger = LogManager.getLogger(ApplicationStatusController.class);

    private ApplicationStatusService applicationStatusService;
    private AppProperties appProperties;

    @Autowired
    public ApplicationStatusController(ApplicationStatusService applicationStatusService,
            AppProperties appProperties) {
        this.applicationStatusService = applicationStatusService;
        this.appProperties = appProperties;
    }

    @GetMapping("/show")
    public String showApplicationPage(Model model) {
        List<UnitDto> availableUnits = new ArrayList<>();
        List<UnitDto> appliedUnits = new ArrayList<>();

        int universityId = SecurityUtils.getUniversityId();
        String userName = SecurityUtils.getUserName();
        logger.info("Request received at: showApplicationPage(), universityId: " + universityId + ", userName: " + userName);
        applicationStatusService.retrieveAvailableUnits(availableUnits, appliedUnits, userName,
                appProperties.getActiveSession(), universityId);

        model.addAttribute("availableUnits", availableUnits);
        model.addAttribute("appliedUnits", appliedUnits);

        return "application/application.html";
    }

    @PostMapping("/apply")
    @ResponseBody
    public ResponseEntity applyUnit(@RequestBody List<Integer> unitIds) {
        int studentId = SecurityUtils.getUserId();
        applicationStatusService.applyUnit(studentId, appProperties.getActiveSession(), unitIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{historyId}")
    @ResponseBody
    public ResponseEntity deleteApplication(@PathVariable("historyId") int historyId) {
        applicationStatusService.deleteApplication(historyId);
        return ResponseEntity.ok().build();
    }
}
