package com.example.asus.teammanager.view.retail_manager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.model.api_model.CustomerArea;
import com.example.asus.teammanager.model.api_model.VisitPlanForApprove;
import com.example.asus.teammanager.model.api_model.VisitPlanList;
import com.example.asus.teammanager.model.api_model.VisitPlanWithCount;
import com.example.asus.teammanager.model.session_manager.SessionManager;
import com.example.asus.teammanager.presenter.customer_presenter.GetCustomerAreaPresenter;
import com.example.asus.teammanager.presenter.visit_plan.GetVisitPlanListPresenter;
import com.example.asus.teammanager.view.global.adapter.PlanListAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class VisitPlanListActivityManager extends AppCompatActivity {

    private Spinner spinner_day;
    private TextView txt_info;
    private RecyclerView rv_plan_list;

    private PlanListAdapter adapter;
    private ArrayList<VisitPlanList> plan_list = new ArrayList<>();
    private ArrayList<CustomerArea> customer_areas = new ArrayList<>();
    private GetVisitPlanListPresenter plan_list_presenter;
    private GetCustomerAreaPresenter customer_area_presenter;
    private SessionManager sm;
    private ArrayList<String> days = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
    private int day;
    private VisitPlanForApprove visit_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_plan_list_manager);

        visit_plan = (VisitPlanForApprove) getIntent().getSerializableExtra("VISIT_PLAN");

        spinner_day = findViewById(R.id.spinner_day);
        txt_info = findViewById(R.id.txt_info);
        rv_plan_list = findViewById(R.id.rv_plan_list);


    }
}
