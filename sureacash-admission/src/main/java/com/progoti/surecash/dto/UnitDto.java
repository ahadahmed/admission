package com.progoti.surecash.dto;

import com.google.common.base.MoreObjects;

public class UnitDto {

    private int id;
    private String name;
    private String code;
    private String description;
    private String universityName;
    private String formattedFees;

    public UnitDto() {}

    public UnitDto(int id, String name, String code, String description,
            String universityName, String formattedFees) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.universityName = universityName;
        this.formattedFees = formattedFees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                .add("id", id)
                .add("name", name)
                .add("code", code)
                .add("description", description)
                .add("universityName", universityName)
                .add("formattedFees", formattedFees)
                .toString();
    }
}
