package com.example.asus.teammanager.view.retail_manager.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.class_project.Functionality;
import com.example.asus.teammanager.model.api_model.VisitPlanForApprove;
import com.example.asus.teammanager.model.api_model.VisitPlanWithCount;
import com.example.asus.teammanager.model.response.Message;
import com.example.asus.teammanager.model.session_manager.SessionManager;
import com.example.asus.teammanager.presenter.GlobalPresenter;
import com.example.asus.teammanager.presenter.date_presenter.DatePresenter;
import com.example.asus.teammanager.presenter.visit_plan.GetMyVisitPlanPresenter;
import com.example.asus.teammanager.presenter.visit_plan.GetVisitPlanForApprovePresenter;
import com.example.asus.teammanager.view.global.adapter.VisitPlanWithCountAdapter;
import com.example.asus.teammanager.view.retail_manager.activity.VisitPlanListActivityManager;
import com.example.asus.teammanager.view.retail_manager.adapter.VisitPlanForApproveAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ApproveVisitPlanFragment extends Fragment implements VisitPlanForApproveAdapter.OnClickVisitPlan{

    private ArrayList<String> years = new ArrayList<>();
    private ArrayList<String> months = new ArrayList<>(Arrays.asList("Select Month", "January","February","March","April","May","June","July","August","September","October","November","December"));
    private ArrayList<String> dates = new ArrayList<>();

    private ArrayList<VisitPlanForApprove> visit_plans = new ArrayList<>();
    private VisitPlanForApproveAdapter adapter;
    private int month, year;
    private int selected_year, selected_month;
    private Spinner spinner_month, spinner_year;
    private TextView txt_info;
    private SessionManager sm;
    private GetVisitPlanForApprovePresenter visit_plan_presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approve_visit_plan, container, false);;

        spinner_year = view.findViewById(R.id.spinner_year);
        spinner_month = view.findViewById(R.id.spinner_month);
        RecyclerView rv_visit_plans = view.findViewById(R.id.rv_visit_plans);
        txt_info = view.findViewById(R.id.txt_info);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);

        years.add("Select Year");
        for(int i=1900;i<=year;i++){
            years.add(String.valueOf(i));
        }

        sm = new SessionManager(getContext());

        visit_plan_presenter = new GetVisitPlanForApprovePresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                ArrayList<VisitPlanForApprove> result = (ArrayList<VisitPlanForApprove>) object;
                visit_plans.clear();

                if(result.size()==0){
                    txt_info.setVisibility(View.VISIBLE);
                    txt_info.setText("Currently no salesman ask for visit plan approval");
                }
                else{
                    txt_info.setVisibility(View.GONE);
                    visit_plans.addAll(result);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String message) {
                visit_plans.clear();
                adapter.notifyDataSetChanged();
                txt_info.setVisibility(View.VISIBLE);

                Message response  = new Gson().fromJson(message, Message.class);
                txt_info.setText(response.getMessage());
            }

            @Override
            public void onFail(String message) {
                visit_plans.clear();
                adapter.notifyDataSetChanged();
                txt_info.setVisibility(View.VISIBLE);
                txt_info.setText(message);
            }
        });
        rv_visit_plans.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new VisitPlanForApproveAdapter(visit_plans);
        adapter.setListener(this);
        rv_visit_plans.setAdapter(adapter);

        setUpSpinner();

//        visit_plan_presenter.getVisitPlanForApprove(sm.getToken().getAccess_token(), selected_month, selected_year);

        return view;
    }

    private void setUpSpinner() {
        ArrayAdapter<String> year_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        ArrayAdapter<String> month_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, months);

        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_year.setAdapter(year_adapter);
        spinner_month.setAdapter(month_adapter);

        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    if(spinner_month.getSelectedItemPosition()!=0){
                        selected_year = Integer.parseInt(parent.getSelectedItem().toString());
                        visit_plan_presenter.getVisitPlanForApprove(sm.getToken().getAccess_token(), selected_month, selected_year);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    if(spinner_year.getSelectedItemPosition()!=0){
                        selected_month = parent.getSelectedItemPosition();
                        visit_plan_presenter.getVisitPlanForApprove(sm.getToken().getAccess_token(), selected_month, selected_year);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_year.setSelection(year_adapter.getPosition(String.valueOf(year)));
        spinner_month.setSelection(month+1);
        selected_month = month+1;
        selected_year = year;

        getActivity().setTitle("Approve Plan");
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        Intent intent = new Intent(getContext(), VisitPlanListActivityManager.class);
        intent.putExtra("VISIT_PLAN", visit_plans.get(position));
        startActivity(intent);
    }
}
