package com.progoti.surecash.admission.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.progoti.surecash.admission.domain.University;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Shaown on 12:47 PM.
 */
public class ProfileUpdateRequest implements Serializable{
    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "image-file")
    private MultipartFile imageFile;

    @Email
    private String email;
    private String address;

    private String contactNo;

    public ProfileUpdateRequest(MultipartFile imageFile, String email, String address, String contactNo) {
        this.imageFile = imageFile;
        this.email = email;
        this.address = address;
        this.contactNo = contactNo;
    }

    public ProfileUpdateRequest() {
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setNonNullValueForUniversityUpdate(University university) throws IOException {
        if(StringUtils.isNotBlank(this.getAddress())){
            university.setAddress(this.getAddress());
        }
        if(StringUtils.isNotBlank(this.getContactNo())){
            university.setContactNo(this.getContactNo());
        }
        if(StringUtils.isNotBlank(this.getEmail())){
            university.setEmail(this.getEmail());
        }
        if(this.getImageFile() != null && this.getImageFile().getSize() > 0){
            university.setLogo(this.getImageFile().getBytes());
        }
    }
}
