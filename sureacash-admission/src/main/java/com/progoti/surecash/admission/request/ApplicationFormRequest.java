package com.progoti.surecash.admission.request;

import com.progoti.surecash.admission.response.StudentInfoResponse;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Created by Shaown on 1:45 PM.
 */
public class ApplicationFormRequest extends StudentInfoResponse{
    @NotEmpty
    private String password;

    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "^01[56789]\\d{8}$", message = "mobile no invalid")
    private String mobile;

    @NotEmpty
    private List<Integer> unitList;

    private Integer universityId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Integer> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<Integer> unitList) {
        this.unitList = unitList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Integer universityId) {
        this.universityId = universityId;
    }
}
