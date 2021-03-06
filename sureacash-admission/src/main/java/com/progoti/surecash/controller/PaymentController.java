package com.progoti.surecash.controller;

import com.progoti.surecash.admission.domain.AdmissionPaymentRequest;
import com.progoti.surecash.admission.request.ReconcileRequest;
import com.progoti.surecash.admission.response.PaymentResponse;
import com.progoti.surecash.admission.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(value = "doPayment", method = RequestMethod.POST)
    public PaymentResponse doPayment(@RequestBody @Valid AdmissionPaymentRequest paymentRequest) throws IOException, URISyntaxException {
        /* request body should have these attributes
        {
            "amount": "",
            "schoolName": "",
            "pin": "",
            "studentId": "",
            "fromWallet": "",
            "externalCustomer": "",
            "channel": ""
        }
         */
        return paymentService.doPayment(paymentRequest);
    }

    @RequestMapping(value = "reconcilePayment", method = RequestMethod.POST)
    public PaymentResponse reconcilePayment(@RequestBody @Valid ReconcileRequest reconcileRequest) {
        /* request body must have these attributes
        {
          "fromWallet": "",
          "amount": "",
          "pin":"",
          "schoolName": "",
          "studentId": "",
          "profinoTransactionId": ""
        }
         */
        return paymentService.reconcilePayment(reconcileRequest);
    }
}
