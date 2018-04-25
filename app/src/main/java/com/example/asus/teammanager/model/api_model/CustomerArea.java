package com.example.asus.teammanager.model.api_model;

import java.io.Serializable;

public class CustomerArea implements Serializable {
    private int id;
    private String name;
    private String code;
    private String created_at;
    private String updated_at;

    public CustomerArea(int id, String name, String code, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
