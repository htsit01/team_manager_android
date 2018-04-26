package com.example.asus.teammanager.model.api_model;

import java.io.Serializable;

public class Customer implements Serializable {
    private int id;
    private double lat;
    private double lng;
    private String name;
    private String billing_address;
    private String shipping_address;
    private String phone;
    private String fax;
    private int customer_area_id;
    private String ktp_img;
    private String shop_img;
    private String created_at;
    private String updated_at;

    public Customer(int id, double lat, double lng, String name, String billing_address, String shipping_address, String phone, String fax, int customer_area_id, String ktp_img, String shop_img, String created_at, String updated_at) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.billing_address = billing_address;
        this.shipping_address = shipping_address;
        this.phone = phone;
        this.fax = fax;
        this.customer_area_id = customer_area_id;
        this.ktp_img = ktp_img;
        this.shop_img = shop_img;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(String billing_address) {
        this.billing_address = billing_address;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public int getCustomer_area_id() {
        return customer_area_id;
    }

    public void setCustomer_area_id(int customer_area_id) {
        this.customer_area_id = customer_area_id;
    }

    public String getKtp_img() {
        return ktp_img;
    }

    public void setKtp_img(String ktp_img) {
        this.ktp_img = ktp_img;
    }

    public String getShop_img() {
        return shop_img;
    }

    public void setShop_img(String shop_img) {
        this.shop_img = shop_img;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
