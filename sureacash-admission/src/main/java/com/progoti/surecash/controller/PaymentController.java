package com.progoti.surecash.controller;

import com.progoti.surecash.admission.service.PaymentService;
import com.progoti.surecash.admission.utility.SecurityUtils;
import com.progoti.surecash.dto.PaymentRequestDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/show")
    public String showPaymentPage(Model model) {
        String userName = SecurityUtils.getUserName();
        List<PaymentRequestDto> paymentRequests = paymentService.listPaymentRequests(userName);
        model.addAttribute("paymentRequests", paymentRequests);
        return "payment/payment.html";
    }
}
