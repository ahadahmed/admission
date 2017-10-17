package com.progoti.surecash.admission.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
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

    @Column(name = "wallet", length = 45)
    @Size(max = 12)
    private String wallet;

    @Column(name = "biller_code", unique = true)
    private String billerCode;

    @OneToMany(mappedBy = "university")
    private List<Unit> unit;

    @OneToMany(mappedBy = "university")
    private List<StudentInfo> studentInfo;

    @OneToMany(mappedBy = "university")
    private List<StudentApplicationHistory> studentApplicationHistoryList;

    @Column(name = "email")
    private String email;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "address")
    private String address;

    @Lob
    @Column(name = "logo", columnDefinition = "mediumblob")
    private byte[] logo;
    
    @Column(name = "domain")
    private String domainName;
    @Column(name = "ip")
    private String ipAddress;

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

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
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

    public List<StudentApplicationHistory> getStudentApplicationHistoryList() {
        return studentApplicationHistoryList;
    }

    public void setStudentApplicationHistoryList(List<StudentApplicationHistory> studentApplicationHistoryList) {
        this.studentApplicationHistoryList = studentApplicationHistoryList;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
    
    
}
