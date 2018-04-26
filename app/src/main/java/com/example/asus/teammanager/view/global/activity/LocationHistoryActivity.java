package com.example.asus.teammanager.view.global.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.model.api_model.Location;
import com.example.asus.teammanager.model.api_model.UserLocation;
import com.example.asus.teammanager.model.response.LocationHistoryResponse;
import com.example.asus.teammanager.model.session_manager.SessionManager;
import com.example.asus.teammanager.presenter.GlobalPresenter;
import com.example.asus.teammanager.presenter.location_presenter.GetLocationHistoryPresenter;
import com.example.asus.teammanager.view.global.adapter.UserLocationHistoryAdapter;

import java.util.ArrayList;

public class LocationHistoryActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView txt_info;
    private RecyclerView rv_users;
    private ArrayList<Location> user_locations = new ArrayList<>();
    private ArrayList<Location> user_checkins = new ArrayList<>();
    private ArrayList<Location> user_checkouts = new ArrayList<>();
    private UserLocationHistoryAdapter adapter;
    private SessionManager sm;
    private int user_id;
    private GetLocationHistoryPresenter locationHistoryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_history);

        String[] types = {"Location","Checkin"};

        spinner = findViewById(R.id.spinnerType);
        txt_info = findViewById(R.id.txt_info);
        rv_users = findViewById(R.id.rv_users);

        sm = new SessionManager(this);
        if(getIntent().hasExtra("id")){
            user_id = getIntent().getIntExtra("id",-1);
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter = new UserLocationHistoryAdapter(user_locations, user_checkins, user_checkouts, position);
                rv_users.setLayoutManager(new LinearLayoutManager(LocationHistoryActivity.this));
                rv_users.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        locationHistoryPresenter = new GetLocationHistoryPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                LocationHistoryResponse response = (LocationHistoryResponse) object;
                user_locations.clear();
                user_checkins.clear();
                user_checkouts.clear();

                user_locations.addAll(response.getLocations());
                user_checkins.addAll(response.getCheckins());
                user_checkouts.addAll(response.getCheckouts());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String message) {
                Toast.makeText(LocationHistoryActivity.this, "Sorry, something went wrong. Error code: ".concat(String.valueOf(code)), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(LocationHistoryActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        locationHistoryPresenter.getLocationHistory(sm.getToken().getAccess_token(), user_id, 2);

        setTitle("Location History");
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
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
