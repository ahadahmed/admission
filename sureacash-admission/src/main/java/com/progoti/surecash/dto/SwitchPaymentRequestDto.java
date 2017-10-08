package com.progoti.surecash.dto;

import com.progoti.surecash.admission.domain.AdmissionPaymentRequest;

/**
 * Created by Shaown on 4:25 PM.
 */
public class SwitchPaymentRequestDto {
    private String fromAc;
    private String toAc;
    private String externalCustomer;
    private String amount;
    private String purpose;
    private String pin;
    private String payFor;
    private String customerRefId;
    private String trnxCode;
    private String channel;

    public String getFromAc() {
        return fromAc;
    }

    public void setFromAc(String fromAc) {
        this.fromAc = fromAc;
    }

    public String getToAc() {
        return toAc;
    }

    public void setToAc(String toAc) {
        this.toAc = toAc;
    }

    public String getExternalCustomer() {
        return externalCustomer;
    }

    public void setExternalCustomer(String externalCustomer) {
        this.externalCustomer = externalCustomer;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPayFor() {
        return payFor;
    }

    public void setPayFor(String payFor) {
        this.payFor = payFor;
    }

    public String getCustomerRefId() {
        return customerRefId;
    }

    public void setCustomerRefId(String customerRefId) {
        this.customerRefId = customerRefId;
    }

    public String getTrnxCode() {
        return trnxCode;
    }

    public void setTrnxCode(String trnxCode) {
        this.trnxCode = trnxCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void doReflectionUsingAdmissionPaymentRequest(AdmissionPaymentRequest request){
        this.fromAc = request.getFromWallet();
        this.toAc = request.getToWallet();
        this.externalCustomer = request.getExternalCustomer();
        this.amount = String.valueOf(request.getAmount());
        this.pin = request.getPin();
        this.customerRefId = request.getApplicantId();
        this.trnxCode = this.externalCustomer == null ? "1202" : "107";
        this.channel = request.getChannel();
    }
}
