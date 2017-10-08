package com.progoti.surecash.dto;

import com.google.common.base.MoreObjects;

public class UnitDto {

    private int unitId;
    private int historyId;
    private String name;
    private String code;
    private String description;
    private String universityName;
    private String formattedFees;

    public UnitDto() {}

    public UnitDto(int unitId, int historyId, String name, String code, String description,
            String universityName, String formattedFees) {
        this.unitId = unitId;
        this.historyId = historyId;
        this.name = name;
        this.code = code;
        this.description = description;
        this.universityName = universityName;
        this.formattedFees = formattedFees;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getFormattedFees() {
        return formattedFees;
    }

    public void setFormattedFees(String formattedFees) {
        this.formattedFees = formattedFees;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("unitId", unitId)
                .add("historyId", historyId)
                .add("name", name)
                .add("code", code)
                .add("description", description)
                .add("universityName", universityName)
                .add("formattedFees", formattedFees)
                .toString();
    }
}
