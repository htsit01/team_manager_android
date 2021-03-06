package com.example.asus.teammanager.view.global.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.class_project.Functionality;
import com.example.asus.teammanager.class_project.GetLocation;
import com.example.asus.teammanager.model.api_model.FollowUp;
import com.example.asus.teammanager.model.response.GeocodingApiResult;
import com.example.asus.teammanager.model.response.Message;
import com.example.asus.teammanager.model.session_manager.SessionManager;
import com.example.asus.teammanager.presenter.GlobalPresenter;
import com.example.asus.teammanager.presenter.customer_presenter.CheckoutPresenter;
import com.example.asus.teammanager.presenter.followup_presenter.FollowUpCheckinPresenter;
import com.example.asus.teammanager.presenter.followup_presenter.FollowUpCheckoutPresenter;
import com.example.asus.teammanager.presenter.followup_presenter.SaveFollowUpReportPresenter;
import com.example.asus.teammanager.presenter.geocoding_presenter.GeocodingPresenter;
import com.google.gson.Gson;

import java.util.Date;

public class FollowUpCheckinActivity extends AppCompatActivity {

    private static final int GPS_SETTING_REQUEST = 100;
    private TextView customer_name, last_action, current_action, checkin_date_time, checkin_status, checkin_error, lbl_error, lbl_status,txt_save;
    private Button btn_checkin, btn_checkout;
    private LinearLayout ll_new_action;
    private EditText checkin_report;
    private String date_time;

    private FollowUp followup;
    private int status;
    private GetLocation location_class;
    private SessionManager sm;
    private double lat, lng;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_checkin);

        customer_name  = findViewById(R.id.customer_name);
        last_action = findViewById(R.id.last_action);
        current_action = findViewById(R.id.current_action);
        checkin_date_time = findViewById(R.id.checkin_date_time);
        checkin_status = findViewById(R.id.checkin_status);
        checkin_error = findViewById(R.id.checkin_error);
        checkin_report = findViewById(R.id.checkin_report);
        btn_checkin = findViewById(R.id.btn_checkin);
        btn_checkout = findViewById(R.id.btn_checkout);
        ll_new_action = findViewById(R.id.ll_new_action);
        lbl_error = findViewById(R.id.lbl_error);
        lbl_status = findViewById(R.id.lbl_status);
        txt_save = findViewById(R.id.txt_save);

        followup = (FollowUp) getIntent().getSerializableExtra("FOLLOWUP");
        type = getIntent().getIntExtra("TYPE",-1);

        status = followup.getStatus_done();
        sm = new SessionManager(this);

        if(type==0){
            customer_name.setText(followup.getName());
        }
        else{
            customer_name.setText(followup.getCustomer().getName());
        }

        if(followup.getReport()!=null){
            checkin_report.setText(followup.getReport());
        }

        if(status==0){
            btn_checkin.setVisibility(View.VISIBLE);
            btn_checkout.setVisibility(View.GONE);
            last_action.setText("You haven\'t do anything here");
        }
        else if(status==1){
            btn_checkin.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.VISIBLE);
            last_action.setText("You have checkin here at ".concat(Functionality.formatDate("yyyy-MM-dd HH:mm:ss", "EEE, dd MMM yyyy", followup.getStart_time())));
        }
        else{
            btn_checkin.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.GONE);
            last_action.setText("You have checkout from here on ".concat(Functionality.formatDate("yyyy-MM-dd HH:mm:ss", "EEE, dd MMM yyyy HH:mm:ss", followup.getStart_time())));
        }

        location_class = new GetLocation(this, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

        btn_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                date_time = Functionality.formatDatetoString(date, "yyyy-MM-dd HH:mm:ss");

                ll_new_action.setVisibility(View.GONE);
                checkin_status.setVisibility(View.GONE);
                checkin_error.setVisibility(View.GONE);
                lbl_error.setVisibility(View.GONE);
                lbl_status.setVisibility(View.GONE);

                if(location_class.canGetLocation()){
                    location_class.findLocation();
                    if(location_class.getLocation() == null){
                        Toast.makeText(FollowUpCheckinActivity.this, "Sorry, we can\'t get your current location now. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        lat = location_class.getLat();
                        lng = location_class.getLng();

                        GeocodingPresenter geocoding_presenter = new GeocodingPresenter(new GlobalPresenter() {
                            @Override
                            public void onSuccess(Object object) {
                                GeocodingApiResult response = (GeocodingApiResult) object;
                                String address = response.getResults().get(0).getFormatted_address();

                                FollowUpCheckinPresenter checkin_presenter = new FollowUpCheckinPresenter(new GlobalPresenter() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        Message response = (Message) object;
                                        Toast.makeText(FollowUpCheckinActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                                        ll_new_action.setVisibility(View.VISIBLE);
                                        checkin_status.setVisibility(View.VISIBLE);
                                        checkin_error.setVisibility(View.GONE);
                                        lbl_error.setVisibility(View.GONE);
                                        lbl_status.setVisibility(View.VISIBLE);

                                        current_action.setText("You have checkin here.");
                                        checkin_date_time.setText(Functionality.formatDate("yyyy-MM-dd HH:mm:ss", "EEE, dd MMM yyyy HH:mm:ss",date_time));
                                        checkin_status.setText("Success");
                                        checkin_status.setTextColor(ContextCompat.getColor(FollowUpCheckinActivity.this,R.color.colorTextGreen));

                                        btn_checkin.setVisibility(View.GONE);
                                        btn_checkout.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onError(int code, String message) {
                                        Message response = new Gson().fromJson(message, Message.class);

                                        ll_new_action.setVisibility(View.VISIBLE);
                                        checkin_status.setVisibility(View.VISIBLE);
                                        checkin_error.setVisibility(View.VISIBLE);
                                        lbl_error.setVisibility(View.VISIBLE);
                                        lbl_status.setVisibility(View.VISIBLE);

                                        current_action.setText("You try to checkin here but failed.");
                                        checkin_date_time.setText(Functionality.formatDate("yyyy-MM-dd HH:mm:ss", "EEE, dd MMM yyyy HH:mm:ss",date_time));
                                        checkin_status.setText("Failed");
                                        checkin_status.setTextColor(ContextCompat.getColor(FollowUpCheckinActivity.this,R.color.colorRed));
                                        checkin_error.setText(response.getMessage());
                                    }

                                    @Override
                                    public void onFail(String message) {
                                        ll_new_action.setVisibility(View.VISIBLE);
                                        checkin_status.setVisibility(View.VISIBLE);
                                        checkin_error.setVisibility(View.VISIBLE);
                                        lbl_error.setVisibility(View.VISIBLE);
                                        lbl_status.setVisibility(View.VISIBLE);

                                        current_action.setText("You try to checkin here but failed.");
                                        checkin_date_time.setText(Functionality.formatDate("yyyy-MM-dd HH:mm:ss", "EEE, dd MMM yyyy HH:mm:ss",date_time));
                                        checkin_status.setText("Failed");
                                        checkin_status.setTextColor(ContextCompat.getColor(FollowUpCheckinActivity.this,R.color.colorRed));
                                        checkin_error.setText(message);
                                    }
                                });
                                checkin_presenter.doCheckin(sm.getToken().getAccess_token(),followup.getId(), lat, lng, address, date_time, type);
                            }

                            @Override
                            public void onError(int code, String message) {
                                Message response = new Gson().fromJson(message, Message.class);
                                ll_new_action.setVisibility(View.VISIBLE);
                                checkin_status.setVisibility(View.VISIBLE);
                                checkin_error.setVisibility(View.VISIBLE);
                                lbl_error.setVisibility(View.VISIBLE);
                                lbl_status.setVisibility(View.VISIBLE);

                                current_action.setText("You try to checkin here but failed.");
                                checkin_date_time.setText(Functionality.formatDate("yyyy-MM-dd HH:mm:ss", "EEE, dd MMM yyyy HH:mm:ss",date_time));
                                checkin_status.setText("Failed");
                                checkin_status.setTextColor(ContextCompat.getColor(FollowUpCheckinActivity.this,R.color.colorRed));
                                checkin_error.setText(response.getMessage());
                            }

                            @Override
                            public void onFail(String message) {
                                ll_new_action.setVisibility(View.VISIBLE);
                                checkin_status.setVisibility(View.VISIBLE);
                                checkin_error.setVisibility(View.VISIBLE);
                                lbl_error.setVisibility(View.VISIBLE);
                                lbl_status.setVisibility(View.VISIBLE);

                                current_action.setText("You try to checkin here but failed.");
                                checkin_date_time.setText(Functionality.formatDate("yyyy-MM-dd HH:mm:ss", "EEE, dd MMM yyyy HH:mm:ss",date_time));
                                checkin_status.setText("Failed");
                                checkin_status.setTextColor(ContextCompat.getColor(FollowUpCheckinActivity.this,R.color.colorRed));
                                lbl_error.setText(message);
                            }
                        });
                        geocoding_presenter.getGeocodingLocation(String.valueOf(lat).concat(",").concat(String.valueOf(lng)),getResources().getString(R.string.geocoding_key));
                    }
                }
                else{
                    turnOnGPSDialog();
                }
            }
        });

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                date_time = Functionality.formatDatetoString(date, "yyyy-MM-dd HH:mm:ss");

                ll_new_action.setVisibility(View.GONE);
                checkin_status.setVisibility(View.GONE);
                checkin_error.setVisibility(View.GONE);
                lbl_error.setVisibility(View.GONE);
                lbl_status.setVisibility(View.GONE);

                if(location_class.canGetLocation()){
                    location_class.findLocation();
                    if(location_class.getLocation()==null){
                        Toast.makeText(FollowUpCheckinActivity.this, "Sorry, we can\'t get your current location no. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        lat = location_class.getLat();
                        lng = location_class.getLng();

                        GeocodingPresenter geocoding_presenter = new GeocodingPresenter(new GlobalPresenter() {
                            @Override
                            public void onSuccess(Object object) {
                                GeocodingApiResult response = (GeocodingApiResult) object;
                                String address = response.getResults().get(0).getFormatted_address();

                                FollowUpCheckoutPresenter checkout_presenter= new FollowUpCheckoutPresenter(new GlobalPresenter() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        Message response = (Message) object;
                                        Toast.makeText(FollowUpCheckinActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                                        ll_new_action.setVisibility(View.VISIBLE);
                                        checkin_status.setVisibility(View.VISIBLE);
                                        checkin_error.setVisibility(View.GONE);
                                        lbl_error.setVisibility(View.GONE);
                                        lbl_status.setVisibility(View.VISIBLE);

                                        current_action.setText("You have checkout from here.");
                                        checkin_date_time.setText(Functionality.formatDate("yyyy-MM-dd HH:mm:ss", "EEE, dd MMM yyyy HH:mm:ss",date_time));
                                        checkin_status.setText("Success");
                                        checkin_status.setTextColor(ContextCompat.getColor(FollowUpCheckinActivity.this,R.color.colorTextGreen));

                                        btn_checkout.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError(int code, String message) {
                                        Message response = new Gson().fromJson(message, Message.class);

                                        ll_new_action.setVisibility(View.VISIBLE);
                                        checkin_status.setVisibility(View.VISIBLE);
                                        checkin_error.setVisibility(View.VISIBLE);
                                        lbl_error.setVisibility(View.VISIBLE);
                                        lbl_status.setVisibility(View.VISIBLE);

                                        current_action.setText("You try to checkout from here but failed.");
                                        checkin_date_time.setText(Functionality.formatDate("yyyy-MM-dd HH:mm:ss", "EEE, dd MMM yyyy HH:mm:ss",date_time));
                                        checkin_status.setText("Failed");
                                        checkin_status.setTextColor(ContextCompat.getColor(FollowUpCheckinActivity.this,R.color.colorRed));
                                        checkin_error.setText(response.getMessage());
                                    }

                                    @Override
                                    public void onFail(String message) {
                                        ll_new_action.setVisibility(View.VISIBLE);
                                        checkin_status.setVisibility(View.VISIBLE);
                                        checkin_error.setVisibility(View.VISIBLE);
                                        lbl_error.setVisibility(View.VISIBLE);
                                        lbl_status.setVisibility(View.VISIBLE);

                                        current_action.setText("You try to checkout from here but failed.");
                                        checkin_date_time.setText(Functionality.formatDate("yyyy-MM-dd HH:mm:ss", "EEE, dd MMM yyyy HH:mm:ss",date_time));
                                        checkin_status.setText("Failed");
                                        checkin_status.setTextColor(ContextCompat.getColor(FollowUpCheckinActivity.this,R.color.colorRed));
                                        checkin_error.setText(message);
                                    }
                                });
                                checkout_presenter.doCheckout(sm.getToken().getAccess_token(),followup.getId(),lat,lng,address,date_time,type);
                            }

                            @Override
                            public void onError(int code, String message) {
                                Message response = new Gson().fromJson(message, Message.class);
                                ll_new_action.setVisibility(View.VISIBLE);
                                checkin_status.setVisibility(View.VISIBLE);
                                checkin_error.setVisibility(View.VISIBLE);
                                lbl_error.setVisibility(View.VISIBLE);
                                lbl_status.setVisibility(View.VISIBLE);

                                current_action.setText("You try to checkout here but failed.");
                                checkin_date_time.setText(Functionality.formatDate("yyyy-MM-dd HH:mm:ss", "EEE, dd MMM yyyy HH:mm:ss",date_time));
                                checkin_status.setText("Failed");
                                checkin_status.setTextColor(ContextCompat.getColor(FollowUpCheckinActivity.this,R.color.colorRed));
                                checkin_error.setText(response.getMessage());
                            }

                            @Override
                            public void onFail(String message) {
                                ll_new_action.setVisibility(View.VISIBLE);
                                checkin_status.setVisibility(View.VISIBLE);
                                checkin_error.setVisibility(View.VISIBLE);
                                lbl_error.setVisibility(View.VISIBLE);
                                lbl_status.setVisibility(View.VISIBLE);

                                current_action.setText("You try to checkin here but failed.");
                                checkin_date_time.setText(Functionality.formatDate("yyyy-MM-dd HH:mm:ss", "EEE, dd MMM yyyy HH:mm:ss",date_time));
                                checkin_status.setText("Failed");
                                checkin_status.setTextColor(ContextCompat.getColor(FollowUpCheckinActivity.this,R.color.colorRed));
                                lbl_error.setText(message);
                            }
                        });
                        geocoding_presenter.getGeocodingLocation(String.valueOf(lat).concat(",").concat(String.valueOf(lng)),getResources().getString(R.string.geocoding_key));
                    }
                }
                else{
                    turnOnGPSDialog();
                }
            }
        });

        txt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveFollowUpReportPresenter report_presenter = new SaveFollowUpReportPresenter(new GlobalPresenter() {
                    @Override
                    public void onSuccess(Object object) {
                        Message response = (Message) object;
                        Toast.makeText(FollowUpCheckinActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(int code, String message) {
                        Message response = new Gson().fromJson(message, Message.class);
                        Toast.makeText(FollowUpCheckinActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(String message) {
                        Toast.makeText(FollowUpCheckinActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
                report_presenter.saveReport(sm.getToken().getAccess_token(),followup.getId(),checkin_report.getText().toString().trim(),type);
            }
        });

        setTitle("Checkin/Checkout");
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void turnOnGPSDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Turn on GPS");
        alertDialog.setMessage("This application need GPS to access your location. Do you want to activate GPS? (Recommend: Yes)");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent,GPS_SETTING_REQUEST);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(FollowUpCheckinActivity.this, "GPS not activated. Operation got cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
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
