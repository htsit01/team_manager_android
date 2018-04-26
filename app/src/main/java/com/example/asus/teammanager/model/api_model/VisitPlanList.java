package com.example.asus.teammanager.model.api_model;

import java.io.Serializable;

public class VisitPlanList implements Serializable{
    private int id;
    private int visit_plan_id;
    private int customer_id;
    private int status_done;
    private String date_time;
    private String start_time;
    private String finish_time;
    private int type;
    private int status_color;
    private String description;
    private String report;
    private String created_at;
    private String updated_at;
    private Customer customer;


    public VisitPlanList(int id, int visit_plan_id, int customer_id, int status_done, String date_time, String start_time, String finish_time, int type, int status_color, String description, String report, String created_at, String updated_at, Customer customer){
        this.id = id;
        this.visit_plan_id = visit_plan_id;
        this.customer_id = customer_id;
        this.status_done = status_done;
        this.date_time = date_time;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.type = type;
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

    public int getVisit_plan_id() {
        return visit_plan_id;
    }

    public void setVisit_plan_id(int visit_plan_id) {
        this.visit_plan_id = visit_plan_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
