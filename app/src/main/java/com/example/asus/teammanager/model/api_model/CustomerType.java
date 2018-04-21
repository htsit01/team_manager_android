package com.example.asus.teammanager.model.api_model;

public class CustomerType {
    private int CustomerTypeID;
    private String CustomerTypeName;
    private String CoUID;
    private int isDisabled;


    public CustomerType(int customerTypeID, String customerTypeName, String coUID, int isDisabled) {
        CustomerTypeID = customerTypeID;
        CustomerTypeName = customerTypeName;
        CoUID = coUID;
        this.isDisabled = isDisabled;
    }

    public int getCustomerTypeID() {
        return CustomerTypeID;
    }

    public void setCustomerTypeID(int customerTypeID) {
        CustomerTypeID = customerTypeID;
    }

    public String getCustomerTypeName() {
        return CustomerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        CustomerTypeName = customerTypeName;
    }

    public String getCoUID() {
        return CoUID;
    }

    public void setCoUID(String coUID) {
        CoUID = coUID;
    }

    public int getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(int isDisabled) {
        this.isDisabled = isDisabled;
    }
}
