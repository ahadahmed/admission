package com.progoti.surecash.admission.domain;

import com.progoti.surecash.admission.utility.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Shaown on 10:55 AM.
 */
@Entity
@Table(name = "student_info")
public class StudentInfo implements Serializable{
    private static final long serialVersionUID = 1L;

    public StudentInfo() { }

    public StudentInfo(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "ssc_roll", length = 45)
    private String sscRoll;

    @Column(name = "ssc_reg", length = 45)
    private String sscReg;

    @Column(name = "ssc_gpa")
    private Double sscGPA;

    @Column(name = "ssc_board")
    @Enumerated(EnumType.STRING)
    private Constants.Board sscBoard;

    @Column(name = "hsc_roll", length = 45)
    private String hscRoll;

    @Column(name = "hsc_reg", length = 45)
    private String hscReg;

    @Column(name = "hsc_gpa")
    private Double hscGPA;

    @Column(name = "hsc_board")
    @Enumerated(EnumType.STRING)
    private Constants.Board hscBoard;

    @Column(name = "ssc_passing_year", length = 4)
    private Integer sscPassingYear;

    @Column(name = "hsc_passing_year", length = 4)
    private Integer hscPassingYear;

    @OneToMany(mappedBy = "studentInfo")
    private List<StudentApplicationHistory> studentApplicationHistory;

    @Column(name = "hsc_group")
    @Enumerated(EnumType.STRING)
    private Constants.Group hscGroup;

    @Column(name = "ssc_group")
    @Enumerated(EnumType.STRING)
    private Constants.Group sscGroup;

    @Lob
    @Column(name = "image_data", columnDefinition = "mediumblob")
    private byte[] image;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "insert_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date insertDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;
    
    @OneToOne(mappedBy = "studentId")
    private User user;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mother_name")
    private String motherName;


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

    public String getSscRoll() {
        return sscRoll;
    }

    public void setSscRoll(String sscRoll) {
        this.sscRoll = sscRoll;
    }

    public String getSscReg() {
        return sscReg;
    }

    public void setSscReg(String sscReg) {
        this.sscReg = sscReg;
    }

    public Double getSscGPA() {
        return sscGPA;
    }

    public void setSscGPA(Double sscGPA) {
        this.sscGPA = sscGPA;
    }

    public String getHscRoll() {
        return hscRoll;
    }

    public void setHscRoll(String hscRoll) {
        this.hscRoll = hscRoll;
    }

    public String getHscReg() {
        return hscReg;
    }

    public void setHscReg(String hscReg) {
        this.hscReg = hscReg;
    }

    public Double getHscGPA() {
        return hscGPA;
    }

    public void setHscGPA(Double hscGPA) {
        this.hscGPA = hscGPA;
    }

    public Integer getSscPassingYear() {
        return sscPassingYear;
    }

    public void setSscPassingYear(Integer sscPassingYear) {
        this.sscPassingYear = sscPassingYear;
    }

    public Integer getHscPassingYear() {
        return hscPassingYear;
    }

    public void setHscPassingYear(Integer hscPassingYear) {
        this.hscPassingYear = hscPassingYear;
    }

    public List<StudentApplicationHistory> getStudentApplicationHistory() {
        return studentApplicationHistory;
    }

    public void setStudentApplicationHistory(List<StudentApplicationHistory> studentApplicationHistory) {
        this.studentApplicationHistory = studentApplicationHistory;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Constants.Board getSscBoard() {
        return sscBoard;
    }

    public void setSscBoard(Constants.Board sscBoard) {
        this.sscBoard = sscBoard;
    }

    public Constants.Board getHscBoard() {
        return hscBoard;
    }

    public void setHscBoard(Constants.Board hscBoard) {
        this.hscBoard = hscBoard;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Constants.Group getHscGroup() {
        return hscGroup;
    }

    public void setHscGroup(Constants.Group hscGroup) {
        this.hscGroup = hscGroup;
    }

    public Constants.Group getSscGroup() {
        return sscGroup;
    }

    public void setSscGroup(Constants.Group sscGroup) {
        this.sscGroup = sscGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
