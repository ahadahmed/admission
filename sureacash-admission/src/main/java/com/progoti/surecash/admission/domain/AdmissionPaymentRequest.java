package com.progoti.surecash.admission.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Shaown on 1:11 PM.
 */
@Entity
@Table(name = "admission_payment_request")
public class AdmissionPaymentRequest implements Serializable{

    // need add trigger on this table
    /*
        CREATE TRIGGER `admission_payment_request_AFTER_INSERT` AFTER INSERT ON `admission_payment_request` FOR EACH ROW
        BEGIN
            INSERT INTO transaction_history (admission_payment_request_id) VALUES(NEW.id);
        END
     */

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "biller_code")
    @JsonProperty("schoolName")
    private String billerCode;

    @Column(name = "applicant_id")
    @JsonProperty("studentId")
    private String applicantId;

    @Column(name = "from_wallet")
    @Size(max = 12)
    private String fromWallet;

    @Column(name = "to_wallet")
    private String toWallet;

    @Column(name = "customer_mobile_no")
    private String externalCustomer;

    @Column(name = "amount")
    private Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "req_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date reqTime = new Date();

    @Column(name = "pin")
    @Size(max = 4)
    private String pin;

    @Column(name = "channel")
    private String channel;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date createDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateDate;

    @OneToOne(mappedBy = "admissionPaymentRequest")
    private TransactionHistory transactionHistory;

//    these property will be used later

//    private Boolean isDeleted = Boolean.FALSE;
//    private Boolean isActive = Boolean.TRUE;
//    private Boolean isArchive = Boolean.FALSE;
//    private String description;
//    private String profinoTransactionId;
//    private String purpose;
//    private String type;
//    private String billNumber;
//    private Long transferTypeId;
//    private String status;
//    private String byWallet;
//    private Boolean ignoreDue = Boolean.FALSE;

    public String getBillerCode() {
        return billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getFromWallet() {
        return fromWallet;
    }

    public void setFromWallet(String fromWallet) {
        this.fromWallet = fromWallet;
    }

    public String getToWallet() {
        return toWallet;
    }

    public void setToWallet(String toWallet) {
        this.toWallet = toWallet;
    }

    public String getExternalCustomer() {
        return externalCustomer;
    }

    public void setExternalCustomer(String externalCustomer) {
        this.externalCustomer = externalCustomer;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(TransactionHistory transactionHistory) {
        this.transactionHistory = transactionHistory;
    }
}
