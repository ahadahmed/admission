package com.progoti.surecash.admission.response;

import com.progoti.surecash.admission.utility.Constants;
import org.apache.commons.codec.binary.Base64;

import java.io.Serializable;

/**
 * Created by Shaown on 3:14 PM.
 */
public class ProfileResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String fatherName;
    private String motherName;
    private Constants.Quota quota;
    private String email;
    private String contactNo;
    private byte[] imageData;
    private String base64Image;

    public ProfileResponse() {
    }

    public ProfileResponse(Constants.Quota quota, String email, String contactNo) {
        this.quota = quota;
        this.email = email;
        this.contactNo = contactNo;
    }

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

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image() {
        this.base64Image = Base64.encodeBase64String(this.imageData);
    }
}
