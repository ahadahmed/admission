package com.progoti.surecash.dto;

import com.google.common.base.MoreObjects;

public class PaymentRequestDto {

    private int id;
    private String applicationId;
    private String unit;
    private String formattedFees;
    private boolean shouldPaid;

    public PaymentRequestDto() { }

    public PaymentRequestDto(int id, String applicationId, String unit, String formattedFees,
            boolean shouldPaid) {
        this.id = id;
        this.applicationId = applicationId;
        this.unit = unit;
        this.formattedFees = formattedFees;
        this.shouldPaid = shouldPaid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFormattedFees() {
        return formattedFees;
    }

    public void setFormattedFees(String formattedFees) {
        this.formattedFees = formattedFees;
    }

    public boolean isShouldPaid() {
        return shouldPaid;
    }

    public void setShouldPaid(boolean shouldPaid) {
        this.shouldPaid = shouldPaid;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("applicationId", applicationId)
                .add("unit", unit)
                .add("formattedFees", formattedFees)
                .add("shouldPaid", shouldPaid)
                .toString();
    }
}
