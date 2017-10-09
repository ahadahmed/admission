package com.progoti.surecash.admission.request;

import com.progoti.surecash.admission.domain.AdmissionPaymentRequest;

import javax.validation.constraints.NotNull;

/**
 * Created by Shaown on 12:41 PM.
 */
public class ReconcileRequest extends AdmissionPaymentRequest{
    @NotNull
    private String profinoTransactionId;

    public String getProfinoTransactionId() {
        return profinoTransactionId;
    }

    public void setProfinoTransactionId(String profinoTransactionId) {
        this.profinoTransactionId = profinoTransactionId;
    }
}
