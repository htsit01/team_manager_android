package com.example.asus.teammanager.view.global.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.model.api_model.CustomerArea;
import com.example.asus.teammanager.model.api_model.FollowUp;
import com.example.asus.teammanager.model.response.Message;
import com.example.asus.teammanager.model.session_manager.SessionManager;
import com.example.asus.teammanager.presenter.GlobalPresenter;
import com.example.asus.teammanager.presenter.customer_presenter.GetCustomerAreaPresenter;
import com.example.asus.teammanager.presenter.followup_presenter.AddFollowUpPresenter;
import com.example.asus.teammanager.presenter.followup_presenter.DeleteFollowUpPresenter;
import com.example.asus.teammanager.presenter.followup_presenter.GetFollowUpPresenter;
import com.example.asus.teammanager.view.global.adapter.FollowUpAdapter;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowUpFragment extends Fragment implements FollowUpAdapter.OnDeleteFollowUp{

    private static final int CHOOSE_FOLLOWUP = 100;
    private static final int ADD_FOLLOWUP = 101;
    private Spinner spinner_status;
    private EditText date_picker;
    private TextView txt_info;

    private Calendar calendar;
    private String date_time;
    private DatePickerDialog.OnDateSetListener date_listener;
    private ArrayList<String> status = new ArrayList<>(Arrays.asList("Active", "History"));
    private boolean is_initial_start = true;
    private GetFollowUpPresenter get_followup_presenter;
    private ArrayList<FollowUp> follow_ups = new ArrayList<>();
    private FollowUpAdapter followup_adapter;
    private SessionManager sm;
    private AddFollowUpPresenter add_followup_presenter;
    private ArrayList<CustomerArea> customer_areas = new ArrayList<>();

    public FollowUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follow_up, container, false);

        date_picker = view.findViewById(R.id.date_picker);
        spinner_status = view.findViewById(R.id.spinner_status);
        txt_info = view.findViewById(R.id.txt_info);
        RecyclerView rv_followups = view.findViewById(R.id.rv_followups);
        FloatingActionButton fab = view.findViewById(R.id.fab_add);

        followup_adapter = new FollowUpAdapter(follow_ups);
        followup_adapter.setInterface(this);
        rv_followups.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_followups.setAdapter(followup_adapter);

        calendar = Calendar.getInstance();
        date_picker.setText(updateLable("EEE, dd MMM yyyy"));
        date_time = updateLable("yyyy-MM-dd");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, status);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_status.setAdapter(adapter);
        spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!is_initial_start){
                    get_followup_presenter.getFollowUp(sm.getToken().getAccess_token(),spinner_status.getSelectedItem().toString().toLowerCase(),date_time);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sm = new SessionManager(getContext());

        date_listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                date_picker.setText(updateLable("EEE, dd MMM yyyy"));
                date_time = updateLable("yyyy-MM-dd");
                if(!is_initial_start){
                    get_followup_presenter.getFollowUp(sm.getToken().getAccess_token(),spinner_status.getSelectedItem().toString().toLowerCase(),date_time);
                }
            }
        };

        GetCustomerAreaPresenter customer_area_presenter = new GetCustomerAreaPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                ArrayList<CustomerArea> result = (ArrayList<CustomerArea>) object;

                customer_areas.clear();
                customer_areas.addAll(result);
            }

            @Override
            public void onError(int code, String message) {
                customer_areas.clear();
            }

            @Override
            public void onFail(String message) {
                customer_areas.clear();
            }
        });
        customer_area_presenter.getCustomerArea(sm.getToken().getAccess_token());
        get_followup_presenter = new GetFollowUpPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                ArrayList<FollowUp> result = (ArrayList<FollowUp>) object;
                follow_ups.clear();

                if(result.size()==0){
                    txt_info.setVisibility(View.VISIBLE);
                    txt_info.setText("No Follow Up plans found");
                }

                else{
                    follow_ups.addAll(result);
                    txt_info.setVisibility(View.GONE);
                }

                followup_adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String message) {
                follow_ups.clear();
                followup_adapter.notifyDataSetChanged();
                Message response = new Gson().fromJson(message, Message.class);
                txt_info.setVisibility(View.VISIBLE);
                txt_info.setText(response.getMessage());
            }

            @Override
            public void onFail(String message) {
                follow_ups.clear();
                followup_adapter.notifyDataSetChanged();
                txt_info.setVisibility(View.VISIBLE);
                txt_info.setText(message);
            }
        });


        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        date_listener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
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

                Date selected;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",new Locale("id", "ID"));
                try {
                    selected = sdf.parse(date_time);
                    if(selected.compareTo(now)<0){
                        Toast.makeText(getContext(), "You cannot add followup plan on past day.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        ChooseFollowUpDialog dialog = new ChooseFollowUpDialog();
                        dialog.setTargetFragment(FollowUpFragment.this, CHOOSE_FOLLOWUP);
                        dialog.show(getFragmentManager(), "ChooseFollowUpDialog");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });

        return view;
    }

    private String updateLable(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("id", "ID"));
        return sdf.format(calendar.getTime());
    }


    @Override
    public void onResume() {
        super.onResume();

        get_followup_presenter.getFollowUp(sm.getToken().getAccess_token(),spinner_status.getSelectedItem().toString().toLowerCase(),date_time);

        is_initial_start = false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== CHOOSE_FOLLOWUP){
            if(resultCode== Activity.RESULT_OK){
                Bundle bundle = data.getExtras();
                int type = bundle.getInt("TYPE");
                AddFollowUpDialog dialog = new AddFollowUpDialog();
                dialog.setTargetFragment(FollowUpFragment.this, ADD_FOLLOWUP);
                dialog.show(getFragmentManager(),"AddFollowUpDialog");
                Bundle new_bundle = new Bundle();
                new_bundle.putInt("TYPE", type);
                new_bundle.putSerializable("CUSTOMER_AREAS", customer_areas);
                dialog.setArguments(new_bundle);
            }
        }

        else if(requestCode == ADD_FOLLOWUP){
            if(resultCode == Activity.RESULT_OK){
                Bundle bundle = data.getExtras();
                int type = bundle.getInt("TYPE");
                String end_user_name = bundle.getString("END_USER_NAME");
                String end_user_address = bundle.getString("END_USER_ADDRESS");
                int customer_id = bundle.getInt("CUSTOMER_ID");
                String date_time = bundle.getString("DATE_TIME");
                String description = bundle.getString("DESCRIPTION");

                if(type == 0){
                    add_followup_presenter = new AddFollowUpPresenter(new GlobalPresenter() {
                        @Override
                        public void onSuccess(Object object) {
                            Message response = (Message) object;
                            Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
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
                    add_followup_presenter.endUserFollowUp(sm.getToken().getAccess_token(), end_user_name, end_user_address, date_time, description);
                }
                else if(type==1){
                    add_followup_presenter = new AddFollowUpPresenter(new GlobalPresenter() {
                        @Override
                        public void onSuccess(Object object) {
                            Message response = (Message) object;
                            Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
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
                    add_followup_presenter.customerFollowUp(sm.getToken().getAccess_token(), customer_id,date_time,description);
                }
            }
        }
    }

    @Override
    public void onDelete(View v) {
        final int position = (int) v.getTag();

        DeleteFollowUpPresenter delete_followup_presenter = new DeleteFollowUpPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                Message response = (Message) object;
                Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();

                follow_ups.remove(position);
                followup_adapter.notifyDataSetChanged();
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
        delete_followup_presenter.deleteFollowUp(sm.getToken().getAccess_token(), follow_ups.get(position).getId(), follow_ups.get(position).getCustomer()==null?0:1);
    }
}
