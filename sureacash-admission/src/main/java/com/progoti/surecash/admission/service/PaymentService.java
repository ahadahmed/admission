package com.progoti.surecash.admission.service;

import com.progoti.surecash.admission.domain.AdmissionPaymentRequest;
import com.progoti.surecash.admission.request.ReconcileRequest;
import com.progoti.surecash.admission.response.PaymentResponse;
import com.progoti.surecash.dto.PaymentRequestDto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface PaymentService {

    List<PaymentRequestDto> listPaymentRequests(String userName);
    PaymentResponse doPayment(AdmissionPaymentRequest paymentRequest) throws IOException, URISyntaxException;
    PaymentResponse reconcilePayment(ReconcileRequest reconcileRequest);
}
