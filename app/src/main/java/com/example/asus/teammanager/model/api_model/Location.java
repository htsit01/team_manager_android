package com.example.asus.teammanager.model.api_model;

public class Location {
    private int id;
    private int user_id;
    private double lat;
    private double lng;
    private String customer;
    private String address;
    private String date_time;
    private String time_since;
    private int status;


    public Location(){
        //empty constructor
    };

    public Location(int id, int user_id, double lat, double lng, String customer, String address, String date_time, String time_since, int status) {
        this.id = id;
        this.user_id = user_id;
        this.lat = lat;
        this.lng = lng;
        this.customer = customer;
        this.address = address;
        this.date_time = date_time;
        this.time_since = time_since;
        this.status = status;
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

    public String getCustomer() {
        return customer;
    }

    public void getCustomer(String customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getTime_since() {
        return time_since;
    }

    public void getTime_since(String time_since) {
        this.time_since = time_since;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
