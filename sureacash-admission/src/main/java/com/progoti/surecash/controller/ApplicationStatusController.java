package com.progoti.surecash.controller;

import com.progoti.surecash.admission.service.ApplicationStatusService;
import com.progoti.surecash.dto.UnitDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/applicationStatus")
public class ApplicationStatusController {

    private ApplicationStatusService applicationStatusService;

    @Autowired
    public ApplicationStatusController(
            ApplicationStatusService applicationStatusService) {
        this.applicationStatusService = applicationStatusService;
    }

    @GetMapping("/show")
    public String showApplicationPage(Model model) {
        List<UnitDto> availableUnits = new ArrayList<>();
        List<UnitDto> appliedUnits = new ArrayList<>();

        applicationStatusService
                .retrieveAvailableUnits(availableUnits, appliedUnits, "testuser", "2017-2018", 1);

        model.addAttribute("availableUnits", availableUnits);
        model.addAttribute("appliedUnits", appliedUnits);

        return "application/application.html";
    }
}
