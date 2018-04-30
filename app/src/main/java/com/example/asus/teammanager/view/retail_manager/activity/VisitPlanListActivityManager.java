package com.example.asus.teammanager.view.retail_manager.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.class_project.Functionality;
import com.example.asus.teammanager.model.api_model.CustomerArea;
import com.example.asus.teammanager.model.api_model.VisitPlanForApprove;
import com.example.asus.teammanager.model.api_model.VisitPlanList;
import com.example.asus.teammanager.model.response.Message;
import com.example.asus.teammanager.model.response.SalesmanPlanListResponse;
import com.example.asus.teammanager.model.session_manager.SessionManager;
import com.example.asus.teammanager.presenter.GlobalPresenter;
import com.example.asus.teammanager.presenter.customer_presenter.GetCustomerAreaPresenter;
import com.example.asus.teammanager.presenter.visit_plan.GetSalesmanVisitPlanListPresenter;
import com.example.asus.teammanager.view.global.adapter.PlanListAdapter;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class VisitPlanListActivityManager extends AppCompatActivity {

    private Spinner spinner_day;
    private TextView txt_info, visit_plan_list_owner, visit_plan_list_date;

    private ArrayList<VisitPlanList> plan_list = new ArrayList<>();
    private ArrayList<CustomerArea> customer_areas = new ArrayList<>();
    private GetSalesmanVisitPlanListPresenter plan_list_presenter;
    private GetCustomerAreaPresenter customer_area_presenter;
    private SessionManager sm;
    private ArrayList<String> days = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
    private int day;
    private VisitPlanForApprove visit_plan;
    private PlanListAdapter adapter;
    private boolean is_first_launch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_plan_list_manager);

        visit_plan = (VisitPlanForApprove) getIntent().getSerializableExtra("VISIT_PLAN");

        spinner_day = findViewById(R.id.spinner_day);
        txt_info = findViewById(R.id.txt_info);
        visit_plan_list_owner = findViewById(R.id.visit_plan_list_owner);
        visit_plan_list_date = findViewById(R.id.visit_plan_list_date);
        RecyclerView rv_plan_list = findViewById(R.id.rv_plan_list);

        adapter = new PlanListAdapter(plan_list, customer_areas);
        rv_plan_list.setLayoutManager(new LinearLayoutManager(this));
        rv_plan_list.setAdapter(adapter);


        sm = new SessionManager(this);
        customer_area_presenter = new GetCustomerAreaPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                ArrayList<CustomerArea> result = (ArrayList<CustomerArea>) object;

                customer_areas.clear();
                customer_areas.addAll(result);
//                adapter.notifyDataSetChanged();
//
//                txt_info.setVisibility(View.GONE);
            }

            @Override
            public void onError(int code, String message) {
//                plan_list.clear();
                customer_areas.clear();
//                adapter.notifyDataSetChanged();
//
//                txt_info.setVisibility(View.GONE);
//                Message response = new Gson().fromJson(message, Message.class);
//                txt_info.setText(response.getMessage());
            }

            @Override
            public void onFail(String message) {
//                plan_list.clear();
                customer_areas.clear();
//                adapter.notifyDataSetChanged();
//
//                txt_info.setVisibility(View.GONE);
//                txt_info.setText(message);
            }
        });
        plan_list_presenter = new GetSalesmanVisitPlanListPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                SalesmanPlanListResponse result = (SalesmanPlanListResponse) object;
                plan_list.clear();
                visit_plan_list_owner.setText("Salesman name: ".concat(result.getSalesman()));
                if(result.getPlan_list().size()==0){
                    txt_info.setVisibility(View.VISIBLE);
                    txt_info.setText("Salesman ".concat(result.getSalesman()).concat(" does not have any plan list"));
                }
                else{
                    txt_info.setVisibility(View.GONE);
                    plan_list.addAll(result.getPlan_list());
                    customer_area_presenter.getCustomerArea(sm.getToken().getAccess_token());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String message) {
                plan_list.clear();
                adapter.notifyDataSetChanged();
                txt_info.setVisibility(View.VISIBLE);

                Message response = new Gson().fromJson(message, Message.class);
                txt_info.setText(response.getMessage());
            }

            @Override
            public void onFail(String message) {
                plan_list.clear();
                adapter.notifyDataSetChanged();
                txt_info.setVisibility(View.VISIBLE);

                txt_info.setText(message);
            }
        });

        ArrayAdapter<String> day_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(day_adapter);

        Calendar calendar = Calendar.getInstance(new Locale("id","ID"));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        if(visit_plan.getStatus()!=null){
            if(visit_plan.getStatus()==2){
                if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
                    day = 6;
                }
                else{
                    day = calendar.get(Calendar.DAY_OF_WEEK) -2;
                }
                spinner_day.setSelection(day);
            }
        }


        spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = spinner_day.getSelectedItemPosition();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", new Locale("id", "ID"));
                try {
                    calendar.setTime(sdf.parse(visit_plan.getValid_date()));
                    calendar.add(Calendar.DATE, day);
                    visit_plan_list_date.setText("Date: ".concat(Functionality.formatDatetoString(calendar.getTime(),"EEE, dd MMM yyyy")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(!is_first_launch){
                    plan_list_presenter.getSalesmanVisitPlanList(sm.getToken().getAccess_token(), visit_plan.getId(),spinner_day.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setTitle("Salesman Plan List");
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        plan_list_presenter.getSalesmanVisitPlanList(sm.getToken().getAccess_token(), visit_plan.getId(),spinner_day.getSelectedItemPosition());
        is_first_launch = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
            case R.id.approve:
                Toast.makeText(this, "approve", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.reject:
                Toast.makeText(this, "reject", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_approve,menu);
        return true;
    }
}
