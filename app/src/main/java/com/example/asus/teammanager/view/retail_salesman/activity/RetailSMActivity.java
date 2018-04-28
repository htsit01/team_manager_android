package com.example.asus.teammanager.view.retail_salesman.activity;

import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.model.response.Message;
import com.example.asus.teammanager.model.session_manager.SessionManager;
import com.example.asus.teammanager.presenter.GlobalPresenter;
import com.example.asus.teammanager.presenter.auth_presenter.LogoutPresenter;
import com.example.asus.teammanager.view.global.fragment.FollowUpFragment;
import com.example.asus.teammanager.view.global.fragment.VisitPlanFragment;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.pusher.pushnotifications.PushNotificationReceivedListener;
import com.pusher.pushnotifications.PushNotifications;

public class RetailSMActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_sm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sm = new SessionManager(this);

        /*
        TODO::check if the user subscribed to this channel (using session manager)
        Do subscribe only if the user hasnt subscribe
         */
        PushNotifications.start(this, "8d1eb444-d7c9-45d6-95a3-cbe1ab9d7253");
        PushNotifications.subscribe("hello");
        PushNotifications.setOnMessageReceivedListener(new PushNotificationReceivedListener() {
            @Override
            public void onMessageReceived(RemoteMessage remoteMessage) {
                Log.i("On Message", remoteMessage.getNotification().getBody());
            }
        });

//        getSupportFragmentManager().beginTransaction().replace(R.id.content_retail_sm, new MainFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.retail_sm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_register) {

        } else if (id == R.id.nav_visit_plan) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_retail_sm, new VisitPlanFragment()).commit();
        } else if (id == R.id.nav_follow_up) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_retail_sm, new FollowUpFragment()).commit();
        } else if (id == R.id.nav_customer_receivable) {

        } else if (id == R.id.nav_achievement) {

        }
        else if (id == R.id.nav_order_region) {

        }
        else if (id == R.id.nav_si_dn) {

        }
        else if (id == R.id.nav_entry_order) {

        }
        else if(id == R.id.nav_log_out){
            LogoutPresenter presenter = new LogoutPresenter(new GlobalPresenter() {
                @Override
                public void onSuccess(Object object) {
                    Message response = (Message) object;
                    Toast.makeText(RetailSMActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                    sm.doClearSession();
                }

                @Override
                public void onError(int code, String message) {
                    Message response  = new Gson().fromJson(message, Message.class);
                    Toast.makeText(RetailSMActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(String message) {
                    Toast.makeText(RetailSMActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
            presenter.doLogout(sm.getToken().getAccess_token());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
