package com.progoti.surecash.admission.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.progoti.surecash.admission.request.AcademicInformationRequest;
import com.progoti.surecash.admission.utility.Constants;

/**
 * Created by Shaown on 12:58 PM.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentInfoResponse {
    private String name;
    private String fatherName;
    private String motherName;
    private Constants.Quota quota;
    private String email;
    private String contactNo;
    public AcademicInformationRequest.AcademicInfo sscInfo;
    public AcademicInformationRequest.AcademicInfo hscInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public Constants.Quota getQuota() {
        return quota;
    }

    public void setQuota(Constants.Quota quota) {
        this.quota = quota;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public AcademicInformationRequest.AcademicInfo getSscInfo() {
        return sscInfo;
    }

    public void setSscInfo(AcademicInformationRequest.AcademicInfo sscInfo) {
        this.sscInfo = sscInfo;
    }

    public AcademicInformationRequest.AcademicInfo getHscInfo() {
        return hscInfo;
    }

    public void setHscInfo(AcademicInformationRequest.AcademicInfo hscInfo) {
        this.hscInfo = hscInfo;
    }
}
