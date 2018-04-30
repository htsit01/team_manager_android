package com.example.asus.teammanager.model.api_model;

import java.io.Serializable;

public class FollowUp  implements Serializable{
    private int id;
    private int user_id;
    private double checkin_lat;
    private double checkin_lng;
    private String checkin_address;
    private double checkout_lat;
    private double checkout_lng;
    private String checkout_address;
    private String name;
    private String address;
    private int status_done;
    private String date_time;
    private String start_time;
    private String finish_time;
    private int status_color;
    private String description;
    private String report;
    private String created_at;
    private String updated_at;
    private Customer customer;

    public FollowUp(int id, int user_id, double checkin_lat, double checkin_lng, String checkin_address, double checkout_lat, double checkout_lng,
                    String checkout_address, String name, String address, int status_done, String date_time, String start_time, String finish_time, int status_color, String description, String report,
                    String created_at, String updated_at, Customer customer) {
        this.id = id;
        this.user_id = user_id;
        this.checkin_lat = checkin_lat;
        this.checkin_lng = checkin_lng;
        this.checkin_address = checkin_address;
        this.checkout_lat = checkout_lat;
        this.checkout_lng = checkout_lng;
        this.checkout_address = checkout_address;
        this.name = name;
        this.address = address;
        this.status_done = status_done;
        this.date_time = date_time;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.status_color = status_color;
        this.description = description;
        this.report = report;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getCheckin_lat() {
        return checkin_lat;
    }

    public void setCheckin_lat(double checkin_lat) {
        this.checkin_lat = checkin_lat;
    }

    public double getCheckin_lng() {
        return checkin_lng;
    }

    public void setCheckin_lng(double checkin_lng) {
        this.checkin_lng = checkin_lng;
    }

    public String getCheckin_address() {
        return checkin_address;
    }

    public void setCheckin_address(String checkin_address) {
        this.checkin_address = checkin_address;
    }

    public double getCheckout_lat() {
        return checkout_lat;
    }

    public void setCheckout_lat(double checkout_lat) {
        this.checkout_lat = checkout_lat;
    }

    public double getCheckout_lng() {
        return checkout_lng;
    }

    public void setCheckout_lng(double checkout_lng) {
        this.checkout_lng = checkout_lng;
    }

    public String getCheckout_address() {
        return checkout_address;
    }

    public void setCheckout_address(String checkout_address) {
        this.checkout_address = checkout_address;
    }

    public int getStatus_done() {
        return status_done;
    }

    public void setStatus_done(int status_done) {
        this.status_done = status_done;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public int getStatus_color() {
        return status_color;
    }

    public void setStatus_color(int status_color) {
        this.status_color = status_color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
