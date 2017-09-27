package com.progoti.surecash.admission.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Shaown on 10:32 AM.
 */
@Entity
@Table(name = "admission_session")
public class AdmissionSession implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "admission_session_year", length = 45)
    private String session;

    @Column(name = "form_price")
    private Double formPrice;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Double getFormPrice() {
        return formPrice;
    }

    public void setFormPrice(Double formPrice) {
        this.formPrice = formPrice;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
