package com.example.asus.teammanager.model.api_model;

public class VisitPlanWithCount extends VisitPlan{

    private int numb_of_plans;
    private int numb_of_done;

    public VisitPlanWithCount(int id, int user_id, String valid_date, Integer status, int is_verify, String created_at, String updated_at, int numb_of_plans, int numb_of_done) {
        super(id, user_id, valid_date, status, is_verify, created_at, updated_at);
        this.numb_of_plans = numb_of_plans;
        this.numb_of_done = numb_of_done;
    }

    public int getNumb_of_plans() {
        return numb_of_plans;
    }

    public void setNumb_of_plans(int numb_of_plans) {
        this.numb_of_plans = numb_of_plans;
    }

    public int getNumb_of_done() {
        return numb_of_done;
    }

    public void setNumb_of_done(int numb_of_done) {
        this.numb_of_done = numb_of_done;
    }
}
