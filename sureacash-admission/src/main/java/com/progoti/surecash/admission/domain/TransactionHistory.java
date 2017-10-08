package com.progoti.surecash.admission.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Shaown on 3:12 PM.
 */
@Entity
@Table(name = "transaction_history")
public class TransactionHistory implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "status", columnDefinition = "VARCHAR(100) DEFAULT 'REQUESTED'")
    private String status;

    @Column(name = "tranx_id")
    private String trnxId;

    @OneToOne
    @JoinColumn(name = "admission_payment_request_id")
    private AdmissionPaymentRequest admissionPaymentRequest;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrnxId() {
        return trnxId;
    }

    public void setTrnxId(String trnxId) {
        this.trnxId = trnxId;
    }

    public AdmissionPaymentRequest getAdmissionPaymentRequest() {
        return admissionPaymentRequest;
    }

    public void setAdmissionPaymentRequest(AdmissionPaymentRequest admissionPaymentRequest) {
        this.admissionPaymentRequest = admissionPaymentRequest;
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
}
