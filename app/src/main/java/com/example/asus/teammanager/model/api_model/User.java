package com.example.asus.teammanager.model.api_model;

public class User {
    private int id;
    private String name;
    private String email;
    private String mac_address;
    private String code; //usercode (biasanya sih initial)
    private String phone;
    private int role_id;
    private int group_id;
    private int customer_area_id;

    public User(int id, String name, String email, String mac_address, String code, String phone, int role_id, int group_id, int customer_area_id) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mac_address = mac_address;
        this.code = code;
        this.phone = phone;
        this.role_id = role_id;
        this.group_id = group_id;
        this.customer_area_id = customer_area_id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getCustomer_area_id() {
        return customer_area_id;
    }

    public void setCustomer_area_id(int customer_area_id) {
        this.customer_area_id = customer_area_id;
    }
}
