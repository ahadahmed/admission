package com.progoti.surecash.admission.service;

import com.progoti.surecash.admission.domain.AdmissionPaymentRequest;
import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.request.ApplicationFormRequest;
import com.progoti.surecash.admission.request.ReconcileRequest;
import com.progoti.surecash.admission.response.CredentialResponse;
import com.progoti.surecash.admission.response.PaymentResponse;
import com.progoti.surecash.admission.response.ProfileResponse;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Shaown on 3:13 PM.
 */
public interface AdmissionService {
    CredentialResponse submitForm(ApplicationFormRequest request);
    ProfileResponse getStudentProfile(StudentInfo studentInfo);
    PaymentResponse doPayment(AdmissionPaymentRequest paymentRequest) throws IOException, URISyntaxException;
    PaymentResponse reconcilePayment(ReconcileRequest reconcileRequest);
}
