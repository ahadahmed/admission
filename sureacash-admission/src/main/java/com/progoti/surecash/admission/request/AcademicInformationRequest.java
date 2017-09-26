package com.progoti.surecash.admission.request;

import com.progoti.surecash.admission.utility.Constants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Shaown on 10:34 AM.
 */
public class AcademicInformationRequest {
    private String securityCode;
    private Constants.Quota quota;
    @Valid
    private AcademicInfo sscInformation;
    @Valid
    private AcademicInfo hscInformation;

    public static class AcademicInfo {
        @NotNull
        private Constants.Board board;
        @NotNull
        private Integer roll;
        @NotNull
        private Integer regNo;
        @NotNull
        private Integer passingYear;
        private double gpa;
        private Constants.Group group;

        public Constants.Board getBoard() {
            return board;
        }

        public void setBoard(Constants.Board board) {
            this.board = board;
        }

        public Integer getRoll() {
            return roll;
        }

        public void setRoll(Integer roll) {
            this.roll = roll;
        }

        public Integer getRegNo() {
            return regNo;
        }

        public void setRegNo(Integer regNo) {
            this.regNo = regNo;
        }

        public Integer getPassingYear() {
            return passingYear;
        }

        public void setPassingYear(Integer passingYear) {
            this.passingYear = passingYear;
        }

        public double getGpa() {
            return gpa;
        }

        public void setGpa(double gpa) {
            this.gpa = gpa;
        }

        public Constants.Group getGroup() {
            return group;
        }

        public void setGroup(Constants.Group group) {
            this.group = group;
        }
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Constants.Quota getQuota() {
        return quota;
    }

    public void setQuota(Constants.Quota quota) {
        this.quota = quota;
    }

    public AcademicInfo getSscInformation() {
        return sscInformation;
    }

    public void setSscInformation(AcademicInfo sscInformation) {
        this.sscInformation = sscInformation;
    }

    public AcademicInfo getHscInformation() {
        return hscInformation;
    }

    public void setHscInformation(AcademicInfo hscInformation) {
        this.hscInformation = hscInformation;
    }

}
