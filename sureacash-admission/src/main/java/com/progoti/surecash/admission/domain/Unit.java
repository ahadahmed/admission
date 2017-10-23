package com.progoti.surecash.admission.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Shaown on 10:22 AM.
 */
@Entity
@Table(name = "unit")
public class Unit implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "unit_name", length = 100)
    private String name;

    @Column(name = "unit_code", length = 4)
    private String code;

    @Column(name = "unit_description", length = 100)
    private String description;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @OneToMany(mappedBy = "unit")
    private List<AdmissionSession> admissionSession;

    @OneToMany(mappedBy = "unit")
    private List<StudentApplicationHistory> studentApplicationHistory;

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

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public List<AdmissionSession> getAdmissionSession() {
        return admissionSession;
    }

    public void setAdmissionSession(List<AdmissionSession> admissionSession) {
        this.admissionSession = admissionSession;
    }

    public List<StudentApplicationHistory> getStudentApplicationHistory() {
        return studentApplicationHistory;
    }

    public void setStudentApplicationHistory(List<StudentApplicationHistory> studentApplicationHistory) {
        this.studentApplicationHistory = studentApplicationHistory;
    }
}
