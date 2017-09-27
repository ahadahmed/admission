package com.progoti.surecash.admission.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Shaown on 10:13 AM.
 */
@Entity
@Table(name = "university")
public class University implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "university_name", length = 100)
    private String name;

    @Column(name = "contact", length = 45)
    private String contact;

    @Column(name = "biller_code", unique = true)
    private String billerCode;

    @OneToMany(mappedBy = "university")
    private List<Unit> unit;

    @OneToMany(mappedBy = "university")
    private List<StudentInfo> studentInfo;

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBillerCode() {
        return billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

    public List<Unit> getUnit() {
        return unit;
    }

    public void setUnit(List<Unit> unit) {
        this.unit = unit;
    }

    public List<StudentInfo> getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(List<StudentInfo> studentInfo) {
        this.studentInfo = studentInfo;
    }
}