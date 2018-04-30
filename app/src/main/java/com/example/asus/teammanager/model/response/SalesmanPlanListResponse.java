package com.example.asus.teammanager.model.response;

import com.example.asus.teammanager.model.api_model.VisitPlanList;

import java.util.ArrayList;

public class SalesmanPlanListResponse {
    private ArrayList<VisitPlanList> plan_list;
    private String salesman;

    public SalesmanPlanListResponse(ArrayList<VisitPlanList> plan_list, String salesman) {
        this.plan_list = plan_list;
        this.salesman = salesman;
    }

    public ArrayList<VisitPlanList> getPlan_list() {
        return plan_list;
    }

    public void setPlan_list(ArrayList<VisitPlanList> plan_list) {
        this.plan_list = plan_list;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }
}
