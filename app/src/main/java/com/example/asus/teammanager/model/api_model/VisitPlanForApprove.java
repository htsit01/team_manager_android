package com.example.asus.teammanager.model.api_model;

public class VisitPlanForApprove extends VisitPlan{

    private String user;

    public VisitPlanForApprove(int id, int user_id, String valid_date, Integer status, int is_verify, String created_at, String updated_at, String user) {
        super(id, user_id, valid_date, status, is_verify, created_at, updated_at);
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
