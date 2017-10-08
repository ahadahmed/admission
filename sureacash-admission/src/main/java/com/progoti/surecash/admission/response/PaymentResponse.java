package com.progoti.surecash.admission.response;

import java.util.Map;

/**
 * Created by Shaown on 6:09 PM.
 */
public class PaymentResponse {
    private String status;
    private String description;
    private String trxnId;
    private String fess;
    private Double balanceFrom;
    private Double balanceTo;

    public PaymentResponse() {
    }

    public PaymentResponse(Map<String, String> responseMap) {
        this.status = responseMap.get("status");
        this.status = responseMap.get("description");
        this.trxnId = responseMap.get("trnxId");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrxnId() {
        return trxnId;
    }

    public void setTrxnId(String trxnId) {
        this.trxnId = trxnId;
    }

    public String getFess() {
        return fess;
    }

    public void setFess(String fess) {
        this.fess = fess;
    }

    public Double getBalanceFrom() {
        return balanceFrom;
    }

    public void setBalanceFrom(Double balanceFrom) {
        this.balanceFrom = balanceFrom;
    }

    public Double getBalanceTo() {
        return balanceTo;
    }

    public void setBalanceTo(Double balanceTo) {
        this.balanceTo = balanceTo;
    }
}
