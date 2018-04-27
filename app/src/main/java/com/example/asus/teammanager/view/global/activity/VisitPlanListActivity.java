package com.example.asus.teammanager.view.global.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.model.api_model.CustomerArea;
import com.example.asus.teammanager.model.api_model.VisitPlanList;
import com.example.asus.teammanager.model.api_model.VisitPlanWithCount;
import com.example.asus.teammanager.model.response.Message;
import com.example.asus.teammanager.model.session_manager.SessionManager;
import com.example.asus.teammanager.presenter.GlobalPresenter;
import com.example.asus.teammanager.presenter.customer_presenter.GetCustomerAreaPresenter;
import com.example.asus.teammanager.presenter.visit_plan.AddVisitPlanListPresenter;
import com.example.asus.teammanager.presenter.visit_plan.DeleteVisitPlanListPresenter;
import com.example.asus.teammanager.presenter.visit_plan.GetVisitPlanListPresenter;
import com.example.asus.teammanager.presenter.visit_plan.UpdateVisitPlanListPresenter;
import com.example.asus.teammanager.view.global.adapter.PlanListAdapter;
import com.example.asus.teammanager.view.global.fragment.AddPlanListDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class VisitPlanListActivity extends AppCompatActivity implements PlanListAdapter.OnDeletePlanList, PlanListAdapter.OnClickPlanList,  AddPlanListDialog.OnSubmit{

    private Spinner spinner_day;
    private Button btn_approval;
    private TextView txt_info;
    private RecyclerView rv_plan_list;
    private FloatingActionButton fab_add;

    private PlanListAdapter adapter;
    private ArrayList<VisitPlanList> plan_list = new ArrayList<>();
    private ArrayList<CustomerArea> customer_areas = new ArrayList<>();
    private GetVisitPlanListPresenter plan_list_presenter;
    private GetCustomerAreaPresenter customer_area_presenter;
    private SessionManager sm;
    private ArrayList<String> days = new ArrayList<>(Arrays.asList("Monday","Tuesday", "Wednesday", "Thursday","Friday", "Saturday", "Sunday"));
    private int day;
    private VisitPlanWithCount visit_plan;
    private boolean is_first_launch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_plan_list);

        visit_plan = (VisitPlanWithCount) getIntent().getSerializableExtra("VISIT_PLAN");

        spinner_day = findViewById(R.id.spinner_day);
        btn_approval = findViewById(R.id.btn_approval);
        txt_info = findViewById(R.id.txt_info);
        rv_plan_list = findViewById(R.id.rv_plan_list);
        fab_add = findViewById(R.id.fab_add);

        adapter = new PlanListAdapter(plan_list, customer_areas);
        adapter.setInterface(this, this);
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
        plan_list_presenter = new GetVisitPlanListPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                ArrayList<VisitPlanList> result = (ArrayList<VisitPlanList>) object;
                plan_list.clear();
                adapter.notifyDataSetChanged();

                if(result.size()==0){
                    txt_info.setVisibility(View.VISIBLE);
                    txt_info.setText("No Plan list found! Add new one by tapping on + button");
                }
                else{
                    txt_info.setVisibility(View.GONE);
                    plan_list.addAll(result);
                    customer_area_presenter.getCustomerArea(sm.getToken().getAccess_token());
                }
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
                if(!is_first_launch){
                    plan_list_presenter.getPlanList(sm.getToken().getAccess_token(), visit_plan.getId(),spinner_day.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(visit_plan.getStatus()!=null){
            if(visit_plan.getStatus()==0||visit_plan.getStatus()==2){
                btn_approval.setVisibility(View.GONE);
            }
            else {
                btn_approval.setVisibility(View.VISIBLE);
            }
        }
        else{
            btn_approval.setVisibility(View.VISIBLE);
        }

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visit_plan.getStatus()!=null){
                    if(visit_plan.getStatus()==0 || visit_plan.getStatus() ==1 ){
                        AddPlanListDialog dialog = new AddPlanListDialog();
                        Bundle args = new Bundle();
                        args.putSerializable("CUSTOMER_AREAS", customer_areas);
                        args.putInt("STATUS", 0);
                        dialog.setArguments(args);
                        dialog.show(getSupportFragmentManager(), "AddPlanListDialog");
                    }
                    else{
                        Toast.makeText(VisitPlanListActivity.this, "Cannot add plan because plan is in pending staus or has been accepted.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    AddPlanListDialog dialog = new AddPlanListDialog();
                    Bundle args = new Bundle();
                    args.putSerializable("CUSTOMER_AREAS", customer_areas);
                    args.putInt("STATUS", 0); //0 add, 1 edit
                    dialog.setArguments(args);
                    dialog.show(getSupportFragmentManager(), "AddPlanListDialog");
                }
            }
        });

        setTitle("My Plan List");
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

//        plan_list_presenter.getPlanList(sm.getToken().getAccess_token(), visit_plan.getId(),spinner_day.getSelectedItemPosition());
    }

    @Override
    public void onDelete(View view) {
        final int position = (int) view.getTag();

        DeleteVisitPlanListPresenter delete_presenter = new DeleteVisitPlanListPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                Message message = (Message) object;
                Toast.makeText(VisitPlanListActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();

                plan_list.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String message) {
                Message response = new Gson().fromJson(message, Message.class);
                Toast.makeText(VisitPlanListActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(VisitPlanListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        delete_presenter.deleteVisitPlanList(sm.getToken().getAccess_token(), plan_list.get(position).getId(), visit_plan.getId());
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();

        if(visit_plan.getStatus()!=null){
            if(visit_plan.getStatus()!=2){
                //show dialog edit
                int status = 1;
                AddPlanListDialog dialog = new AddPlanListDialog();
                Bundle args = new Bundle();
                args.putSerializable("CUSTOMER_AREAS", customer_areas);
                args.putInt("STATUS", status);//0 add, 1 edit
                args.putString("DESCRIPTION", plan_list.get(position).getDescription());
                args.putInt("TYPE", plan_list.get(position).getType());
                args.putInt("PLAN_ID", plan_list.get(position).getId());
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "EditPlanListDialog");
            }
            else{
                Intent intent = new Intent(this, CheckinActivity.class);
                intent.putExtra("PLAN_LIST", plan_list.get(position));
                intent.putExtra("STATUS",visit_plan.getStatus());
                startActivity(intent);
            }

        }else{
            //show dialog edit
            int status = 1;
            AddPlanListDialog dialog = new AddPlanListDialog();
            Bundle args = new Bundle();
            args.putSerializable("CUSTOMER_AREAS", customer_areas);
            args.putInt("STATUS", status);//0 add, 1 edit
            args.putString("DESCRIPTION", plan_list.get(position).getDescription());
            args.putInt("TYPE", plan_list.get(position).getType());
            args.putInt("PLAN_ID", plan_list.get(position).getId());
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "EditPlanListDialog");
        }
    }

    @Override
    public void onAdd(int customer_id, int type, String description) {
        AddVisitPlanListPresenter add_plan_presenter = new AddVisitPlanListPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                Message message = (Message) object;
                Toast.makeText(VisitPlanListActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();

                plan_list_presenter.getPlanList(sm.getToken().getAccess_token(), visit_plan.getId(),spinner_day.getSelectedItemPosition());
            }

            @Override
            public void onError(int code, String message) {

                Toast.makeText(VisitPlanListActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(VisitPlanListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        add_plan_presenter.AddVisitPlanList(sm.getToken().getAccess_token(), visit_plan.getId(), customer_id, day, type, description);

    }

    @Override
    public void onEdit(int plan_id, int customer_id, int type, String description) {
        UpdateVisitPlanListPresenter delete_plan_presenter = new UpdateVisitPlanListPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                Message message = (Message) object;
                Toast.makeText(VisitPlanListActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                plan_list_presenter.getPlanList(sm.getToken().getAccess_token(), visit_plan.getId(),spinner_day.getSelectedItemPosition());
            }

            @Override
            public void onError(int code, String message) {
                Message response = new Gson().fromJson(message, Message.class);
                Toast.makeText(VisitPlanListActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(VisitPlanListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        delete_plan_presenter.updateVisitPlanList(sm.getToken().getAccess_token(), plan_id, visit_plan.getId(), customer_id, day, type, description);
    }

    @Override
    protected void onResume() {
        super.onResume();
        plan_list_presenter.getPlanList(sm.getToken().getAccess_token(), visit_plan.getId(),spinner_day.getSelectedItemPosition());
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
