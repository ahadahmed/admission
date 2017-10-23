package com.progoti.surecash.controller;

import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.domain.User;
import com.progoti.surecash.admission.domain.UserDetailsImpl;
import com.progoti.surecash.admission.repository.StudentInfoRepository;
import com.progoti.surecash.admission.repository.UserRepository;
import com.progoti.surecash.admission.response.ProfileResponse;
import com.progoti.surecash.admission.service.AdmissionService;
import com.progoti.surecash.admission.service.ApplicationStatusService;
import com.progoti.surecash.admission.service.PaymentService;
import com.progoti.surecash.admission.utility.AppProperties;
import com.progoti.surecash.admission.utility.SecurityUtils;
import com.progoti.surecash.dto.PaymentRequestDto;
import com.progoti.surecash.dto.UnitDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    private ApplicationStatusService applicationStatusService;
    private AppProperties appProperties;
    private AdmissionService admissionService;
    private StudentInfoRepository studentInfoRepository;
    private UserRepository userRepository;
    private PaymentService paymentService;

    @Autowired
    public UserController(ApplicationStatusService applicationStatusService,
                          AppProperties appProperties, AdmissionService admissionService, StudentInfoRepository studentInfoRepository,
                          UserRepository userRepository, PaymentService paymentService) {
        this.applicationStatusService = applicationStatusService;
        this.appProperties = appProperties;
        this.admissionService = admissionService;
        this.studentInfoRepository = studentInfoRepository;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
    }

    @GetMapping("/applicationStatus/show")
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

    @GetMapping("/payment/show")
    public String showPaymentPage(Model model, HttpServletRequest servletRequest) {
        String userName = servletRequest.getUserPrincipal().getName();
        University university = (University) servletRequest.getServletContext().getAttribute(servletRequest.getServerName());
        List<PaymentRequestDto> paymentRequests = paymentService.listPaymentRequests(userName, university);
        model.addAttribute("paymentRequests", paymentRequests);
        return "payment/payment.html";
    }

    @PostMapping("/applicationStatus/apply")
    @ResponseBody
    public ResponseEntity applyUnit(@RequestBody List<Integer> unitIds) {
        int studentId = SecurityUtils.getUserId();
        applicationStatusService.applyUnit(studentId, appProperties.getActiveSession(), unitIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/applicationStatus/delete/{historyId}")
    @ResponseBody
    public ResponseEntity deleteApplication(@PathVariable("historyId") int historyId) {
        applicationStatusService.deleteApplication(historyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/edit-profile")
    public String editProfile(Model model) {
        UserDetailsImpl userDetails = SecurityUtils.getUserDetails();
        ProfileResponse profile = admissionService.getStudentProfile(studentInfoRepository.findOne(userDetails.getUser().getStudentId().getId()));
        model.addAttribute("profile", profile);
        return "application/edit_profile";
    }

    @PostMapping(value = "profile-update")
    @ResponseBody
    public ResponseEntity uploadImageFile(@RequestParam(value = "image-file", required = false) MultipartFile imageFile,
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
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dashboard")
    public String showUserDashboard(Model model, HttpServletRequest request) {
        return "application/user_dashboard";
    }
}
