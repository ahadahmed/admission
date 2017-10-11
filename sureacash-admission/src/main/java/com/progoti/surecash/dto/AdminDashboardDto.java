package com.progoti.surecash.dto;

import com.progoti.surecash.admission.utility.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaown on 6:41 AM.
 */
public class AdminDashboardDto {
    private int unitId;
    private String unitCode;
    private String unit;
    private long applicantCount;
    private long paidApplicant;
    private long quotaApplicant;
    private List<Object[]> groupStatusList;
    private List<Object[]> boardStatusList;

    public AdminDashboardDto() {
    }

    public AdminDashboardDto(int unitId, String unit, String unitCode, long applicantCount, long paidApplicant, long quotaApplicant) {
        this.unitId = unitId;
        this.unit = unit;
        this.unitCode = unitCode;
        this.applicantCount = applicantCount;
        this.paidApplicant = paidApplicant;
        this.quotaApplicant = quotaApplicant;
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

    public long getQuotaApplicant() {
        return quotaApplicant;
    }

    public void setQuotaApplicant(long quotaApplicant) {
        this.quotaApplicant = quotaApplicant;
    }

    public List<Object[]> getGroupStatusList() {
        return groupStatusList;
    }

    public void setGroupStatusList(List<Object[]> groupStatusList) {
        this.groupStatusList = groupStatusList;
    }

    public List<Object[]> getBoardStatusList() {
        return boardStatusList;
    }

    public void setBoardStatusList(List<Object[]> boardStatusList) {
        this.boardStatusList = boardStatusList;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    @Override
    public String toString() {
        return this.getUnit() + "--->" + this.getApplicantCount() + "--->" + this.getPaidApplicant();
    }
}
