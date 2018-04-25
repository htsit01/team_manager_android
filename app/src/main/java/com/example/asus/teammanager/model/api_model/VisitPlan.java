package com.example.asus.teammanager.model.api_model;

import java.io.Serializable;

public class VisitPlan implements Serializable{
    private int id;
    private int user_id;
    private String valid_date;
    private Integer status; //we use Integer because status can be null
    private int is_verify;
    private String created_at;
    private String updated_at;

    public VisitPlan(int id, int user_id, String valid_date, Integer status, int is_verify, String created_at, String updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.valid_date = valid_date;
        this.status = status;
        this.is_verify = is_verify;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getIs_verify() {
        return is_verify;
    }

    public void setIs_verify(int is_verify) {
        this.is_verify = is_verify;
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
