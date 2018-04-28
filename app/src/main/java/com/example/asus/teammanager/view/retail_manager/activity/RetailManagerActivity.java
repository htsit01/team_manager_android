package com.example.asus.teammanager.view.retail_manager.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
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
import com.example.asus.teammanager.view.retail_salesman.activity.RetailSMActivity;
import com.google.gson.Gson;

public class RetailManagerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.retail_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_register) {

        } else if (id == R.id.nav_approve_plan) {

        } else if (id == R.id.nav_visit_report) {

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
                    Toast.makeText(RetailManagerActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                    sm.doClearSession();
                }

                @Override
                public void onError(int code, String message) {
                    Message response  = new Gson().fromJson(message, Message.class);
                    Toast.makeText(RetailManagerActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(String message) {
                    Toast.makeText(RetailManagerActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
            presenter.doLogout(sm.getToken().getAccess_token());
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
