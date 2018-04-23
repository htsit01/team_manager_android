package com.example.asus.teammanager.view.global.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.class_project.Functionality;
import com.example.asus.teammanager.model.api_model.User;
import com.example.asus.teammanager.model.response.LoginResponse;
import com.example.asus.teammanager.model.response.Message;
import com.example.asus.teammanager.model.session_manager.SessionManager;
import com.example.asus.teammanager.presenter.GlobalPresenter;
import com.example.asus.teammanager.presenter.auth_presenter.LoginPresenter;
import com.example.asus.teammanager.presenter.user_presenter.GetUserPresenter;
import com.example.asus.teammanager.view.retail_salesmanager.activity.RetailSMActivity;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.pusher.pushnotifications.PushNotificationReceivedListener;
import com.pusher.pushnotifications.PushNotifications;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout lbl_email, lbl_password;
    private TextInputEditText input_email, input_password;
    private ImageView img_logo;
    private SessionManager sm;
    private GetUserPresenter userPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        lbl_email = findViewById(R.id.lbl_email);
        lbl_password = findViewById(R.id.lbl_password);
        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        Button btn_login = findViewById(R.id.btn_login);
        img_logo = findViewById(R.id.img_logo);
        sm = new SessionManager(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    toggleShowError(false);
                    LoginPresenter loginPresenter = new LoginPresenter(new GlobalPresenter() {
                        @Override
                        public void onSuccess(Object object) {
                            LoginResponse response = (LoginResponse) object;
                            sm.doCreateSession(response);
                            userPresenter = new GetUserPresenter(new GlobalPresenter() {
                                @Override
                                public void onSuccess(Object object) {
                                    User response = (User) object;
                                    sm.doSaveUser(response);
                                    Toast.makeText(LoginActivity.this, "User login successfully.", Toast.LENGTH_SHORT).show();

                                    if(response.getRole_id()==1){
                                        Intent intent = new Intent(LoginActivity.this, RetailSMActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    //redirect to each user role page
                                }

                                @Override
                                public void onError(int code, String message) {
                                    if(code ==401){
                                        Message response = new Gson().fromJson(message, Message.class);

                                        Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "Something went wrong, error code: ".concat(String.valueOf(code)), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFail(String message) {
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });

                            userPresenter.getUser(sm.getToken().getAccess_token());
                        }

                        @Override
                        public void onError(int code, String message) {
                            if(code ==401){
                                Message response = new Gson().fromJson(message, Message.class);

                                Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Something went wrong, error code: ".concat(String.valueOf(code)), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFail(String message) {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    });
                    loginPresenter.postLogin(input_email.getText().toString().trim(), input_password.getText().toString().trim(), Functionality.getMacAddress());
                }
                else{
                    toggleShowError(false);
                    if(TextUtils.isEmpty(input_email.getText().toString().trim())){
                        lbl_email.setErrorEnabled(true);
                        lbl_email.setError("Email is required.");
                    }
                    if(TextUtils.isEmpty(input_password.getText().toString().trim())){
                        lbl_password.setErrorEnabled(true);
                        lbl_email.setError("Password is required.");
                    }
                }
            }
        });

        /*
        This function is for checking whether user is login
        system will get the "is login" status from Class session manager
        session manager will save is login status every time the user login


        if user login , then we check the user role id is null or not
        if null it means, user data hasnt been saved to our system (probably fail get user data on login)
        if not null it means, we already has the user data then the user are save to use RetailSMActivity

         */
        if(sm.isUserLogin()){
            /*
            ONLY IF WE KNOW THE USER ROLE ID / only if user is not null
             */
            if(sm.getUser()!=null){
                if(sm.getUser().getRole_id()==1){
                    Intent intent = new Intent(LoginActivity.this, RetailSMActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    public boolean isValid(){
        if(!TextUtils.isEmpty(input_email.getText().toString().trim()) && !TextUtils.isEmpty(input_password.getText().toString().trim())){
            return true;
        }

        return false;
    }

    public void toggleShowError(boolean status){
        lbl_email.setErrorEnabled(status);
        lbl_password.setErrorEnabled(status);
    }
}
