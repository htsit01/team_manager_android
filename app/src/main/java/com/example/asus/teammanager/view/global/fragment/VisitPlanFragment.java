package com.example.asus.teammanager.view.global.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.asus.teammanager.model.api_model.VisitPlanWithCount;
import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.model.response.Message;
import com.example.asus.teammanager.model.session_manager.SessionManager;
import com.example.asus.teammanager.presenter.GlobalPresenter;
import com.example.asus.teammanager.presenter.date_presenter.DatePresenter;
import com.example.asus.teammanager.presenter.visit_plan.AddVisitPlanPresenter;
import com.example.asus.teammanager.presenter.visit_plan.DeleteMyVisitPlanPresenter;
import com.example.asus.teammanager.presenter.visit_plan.GetMyVisitPlanPresenter;
import com.example.asus.teammanager.view.global.activity.VisitPlanListActivity;
import com.example.asus.teammanager.view.global.adapter.VisitPlanWithCountAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisitPlanFragment extends Fragment implements VisitPlanWithCountAdapter.onDeleteVisitPlan, VisitPlanWithCountAdapter.onClickVisitPlan{

    private ArrayList<String> years = new ArrayList<>();
    private ArrayList<String> months = new ArrayList<>(Arrays.asList("Select Month", "January","February","March","April","May","June","July","August","September","October","November","December"));
    private ArrayList<String> dates = new ArrayList<>();


    private GetMyVisitPlanPresenter plan_presenter;
    private DatePresenter date_presenter;
    private ArrayList<VisitPlanWithCount> visit_plans = new ArrayList<>();
    private VisitPlanWithCountAdapter adapter;
    private int month, year;
    private String selected_date;
    private int selected_year, selected_month;
    private Spinner spinner_month, spinner_year, spinner_date;
    private ArrayAdapter<String> date_adapter;
    private TextView txt_info;
    private SessionManager sm;

    public VisitPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visit_plan, container, false);
        spinner_year = view.findViewById(R.id.spinner_year);
        spinner_month = view.findViewById(R.id.spinner_month);
        spinner_date = view.findViewById(R.id.spinner_date);
        RecyclerView rv_visit_plans = view.findViewById(R.id.rv_visit_plans);
        txt_info = view.findViewById(R.id.txt_info);
        FloatingActionButton fab_add = view.findViewById(R.id.fab_add);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);

        years.add("Select Year");
        for(int i=1900;i<=year;i++){
            years.add(String.valueOf(i));
        }

        sm = new SessionManager(getContext());
        plan_presenter = new GetMyVisitPlanPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                ArrayList<VisitPlanWithCount> result = (ArrayList<VisitPlanWithCount>) object;
                visit_plans.clear();
                adapter.notifyDataSetChanged();
                if(result.size()==0){
                    txt_info.setVisibility(View.VISIBLE);
                    txt_info.setText("No Visit Plan found! Add new one by tapping on + button");
                }
                else{
                    txt_info.setVisibility(View.GONE);
                    visit_plans.addAll(result);
                }
            }

            @Override
            public void onError(int code, String message) {
                visit_plans.clear();
                adapter.notifyDataSetChanged();
                txt_info.setVisibility(View.VISIBLE);

                Message response  = new Gson().fromJson(message, Message.class);
                txt_info.setText(response.getMessage());
//                txt_info.setText("Sorry, Something went wrong. Error code: ".concat(String.valueOf(code)));
            }

            @Override
            public void onFail(String message) {
                visit_plans.clear();
                adapter.notifyDataSetChanged();
                txt_info.setVisibility(View.VISIBLE);
                txt_info.setText(message);
            }
        });
        date_presenter = new DatePresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                ArrayList<String> result = (ArrayList<String>) object;
                dates.clear();
                for(String item : result){
                    dates.add(Functionality.formatDate("yyyy-MM-dd", "EEE, dd MMM yyyy", item));
                }

                date_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dates);
                date_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_date.setAdapter(date_adapter);
            }

            @Override
            public void onError(int code, String message) {
                dates.clear();
                date_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dates);
                date_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_date.setAdapter(date_adapter);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String message) {
                dates.clear();
                date_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dates);
                date_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_date.setAdapter(date_adapter);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        rv_visit_plans.setLayoutManager( new LinearLayoutManager(getContext()));
        adapter = new VisitPlanWithCountAdapter(visit_plans);
        adapter.setInteface(this, this);
        rv_visit_plans.setAdapter(adapter);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date now = new Date();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(now);
                calendar.set(Calendar.HOUR_OF_DAY,0);
                calendar.set(Calendar.MINUTE,0);
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                now= calendar.getTime();
                Log.e("date", now.toString());
                Date selected;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",new Locale("id", "ID"));
                try {
                    selected = sdf.parse(selected_date);
                    Log.e("date", selected_date);
                    if(selected.compareTo(now)<0){
                        Toast.makeText(getContext(), "You cannot add visit plan on past day.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        AddVisitPlanPresenter addVisitPlanPresenter = new AddVisitPlanPresenter(new GlobalPresenter() {
                            @Override
                            public void onSuccess(Object object) {
                                Message message = (Message) object;
                                Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_SHORT).show();
                                plan_presenter.getMyVisitPlan(sm.getToken().getAccess_token(),selected_date);
                            }

                            @Override
                            public void onError(int code, String message) {
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFail(String message) {
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        });
                        addVisitPlanPresenter.addVisitPlan(sm.getToken().getAccess_token(), selected_date);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        setUpSpinner();

        return view;
    }

    private void setUpSpinner() {
        ArrayAdapter<String> year_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        ArrayAdapter<String> month_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, months);
        date_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dates);

        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_year.setAdapter(year_adapter);
        spinner_month.setAdapter(month_adapter);
        spinner_date.setAdapter(date_adapter);

        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    if(spinner_month.getSelectedItemPosition()!=0){
                        selected_year = Integer.parseInt(parent.getSelectedItem().toString());
                        date_presenter.getFirstMonday(selected_year, selected_month);
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
                        date_presenter.getFirstMonday(selected_year,selected_month);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_date = Functionality.formatDate("EEE, dd MMM yyyy", "yyyy-MM-dd", parent.getSelectedItem().toString());
                Log.e("date", parent.getSelectedItem().toString());
                if(!TextUtils.isEmpty(selected_date.trim())||selected_date!=null){
                    plan_presenter.getMyVisitPlan(sm.getToken().getAccess_token(),selected_date);
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
    }

    @Override
    public void onDelete(View view) {
        int id = (int) view.getTag();

        DeleteMyVisitPlanPresenter delete_presenter = new DeleteMyVisitPlanPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                Message message = (Message) object;
                Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_SHORT).show();

                visit_plans.clear();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String message) {
                Message response = new Gson().fromJson(message, Message.class);
                Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        delete_presenter.deleteMyVisitPlan(sm.getToken().getAccess_token(), id);
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();

        Intent intent = new Intent(getContext(), VisitPlanListActivity.class);
        intent.putExtra("VISIT_PLAN",visit_plans.get(position));
        startActivity(intent);
    }
}
