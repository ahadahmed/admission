package com.progoti.surecash.dto;

import com.google.common.base.MoreObjects;

public class ContactDto {

    private String universityName;
    private String universityAddress;
    private String contactNo;
    private String contactMobile;
    private String contactEmail;
    private byte[] universityLogo;

    public ContactDto() { }

    public ContactDto(String universityName, String universityAddress, String contactNo,
            String contactMobile, String contactEmail, byte[] universityLogo) {
        this.universityName = universityName;
        this.universityAddress = universityAddress;
        this.contactNo = contactNo;
        this.contactMobile = contactMobile;
        this.contactEmail = contactEmail;
        this.universityLogo = universityLogo;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getUniversityAddress() {
        return universityAddress;
    }

    public void setUniversityAddress(String universityAddress) {
        this.universityAddress = universityAddress;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public byte[] getUniversityLogo() {
        return universityLogo;
    }

    public void setUniversityLogo(byte[] universityLogo) {
        this.universityLogo = universityLogo;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("universityName", universityName)
                .add("universityAddress", universityAddress)
                .add("contactNo", contactNo)
                .add("contactMobile", contactMobile)
                .add("contactEmail", contactEmail)
                .toString();
    }
}
