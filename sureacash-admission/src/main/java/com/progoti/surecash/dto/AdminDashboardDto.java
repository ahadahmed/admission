package com.progoti.surecash.dto;

/**
 * Created by Shaown on 6:41 AM.
 */
public class AdminDashboardDto {
    private int unitId;
    private String unit;
    private long applicantCount;
    private long paidApplicant;

    public AdminDashboardDto() {
    }

    public AdminDashboardDto(int unitId, String unit, long applicantCount, long paidApplicant) {
        this.unitId = unitId;
        this.unit = unit;
        this.applicantCount = applicantCount;
        this.paidApplicant = paidApplicant;
    }

    public long getPaidApplicant() {
        return paidApplicant;
    }

    public void setPaidApplicant(long paidApplicant) {
        this.paidApplicant = paidApplicant;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getApplicantCount() {
        return applicantCount;
    }

    public void setApplicantCount(long applicantCount) {
        this.applicantCount = applicantCount;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    @Override
    public String toString() {
        return this.getUnit() + "--->" + this.getApplicantCount() + "--->" + this.getPaidApplicant();
    }
}
