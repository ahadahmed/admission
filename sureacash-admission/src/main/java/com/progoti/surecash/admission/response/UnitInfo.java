package com.progoti.surecash.admission.response;

public class UnitInfo {
    private int id;
    private String unitName;
    private String unitDescription;
    private String fees;

    public UnitInfo() {
    }

    public UnitInfo(int id, String unitName, String unitDescription, String fees) {
        this.id = id;
        this.unitName = unitName;
        this.unitDescription = unitDescription;
        this.fees = fees;
    }

    public String getUnitName(){
        return unitName;
    }

    public String getUnitDescription(){
        return unitDescription;
    }

    public String getFees() {
        return fees;
    }

    public void setUnitName(String unitName){
        this.unitName=unitName;
    }
    public void setUnitDescription(String unitDescription){
        this.unitDescription =unitDescription;
    }
    public void setFees(String fees){
        this.fees=fees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
