package com.progoti.surecash.admission.response;

public class AdmissionInfo {
    private String unitName;
    private String unitDescription;
    private String fees;

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

}
