package com.progoti.surecash.admission.domain;

import com.progoti.surecash.admission.utility.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by Shaown on 10:55 AM.
 */
@Entity
@Table(name = "student_info", uniqueConstraints = @UniqueConstraint(columnNames = {"university_id", "user_name"}))
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

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "user_name", length = 100)
    private String userName;

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

    @Column(name = "quota")
    @Enumerated(EnumType.STRING)
    private Constants.Quota quota;

    @Column(name = "mobile_no", length = 11)
    private String mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "ssc_passing_year", length = 4)
    private Integer sscPassingYear;

    @Column(name = "hsc_passing_year", length = 4)
    private Integer hscPassingYear;

    @OneToMany(mappedBy = "studentInfo")
    private List<StudentApplicationHistory> studentApplicationHistory;

    @Column(name = "hsc_group")
    @Enumerated(EnumType.STRING)
    private Constants.Group group;

    /*@ManyToOne
	@JoinColumn(name = "role_id")
	StudentInfo studentInfo;*/

    @Lob
    @Column(name = "image_data", columnDefinition = "mediumblob")
    private byte[] image;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
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

	/*public Set<Role> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<Role> userRole) {
		this.userRole = userRole;
	}*/

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

    public Constants.Quota getQuota() {
        return quota;
    }

    public void setQuota(Constants.Quota quota) {
        this.quota = quota;
    }

    public Constants.Group getGroup() {
        return group;
    }

    public void setGroup(Constants.Group group) {
        this.group = group;
    }
}
