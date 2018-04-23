package com.example.asus.teammanager.model.session_manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.asus.teammanager.model.api_model.User;
import com.example.asus.teammanager.model.response.LoginResponse;
import com.google.gson.Gson;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    private static final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "USER_SESSION";
    private static final String KEY_IS_LOGIN = "IS_USER_LOGIN";
    private static final String KEY_IS_SUBSCRIBED = "IS_USER_SUBSCRIBED";
    private static final String KEY_USER = "USER";
    private static final String KEY_TOKEN = "TOKEN";
    private static final String KEY_LAST_LAT = "LAST_LAT";
    private static final String KEY_LAST_LNG = "LAST_LNG";


    public SessionManager (Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public void doCreateSession(LoginResponse token){
        editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGIN, true);
        editor.putString(KEY_TOKEN, new Gson().toJson(token));
        editor.apply();
    }

    public void doClearSession(){
        editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    //untuk refresh token options
    public void doSaveToken(LoginResponse token){
        editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, new Gson().toJson(token));
        editor.apply();
    }

    public LoginResponse getToken(){
        return new Gson().fromJson(sharedPreferences.getString(KEY_TOKEN,""),LoginResponse.class);
    }

    public void doSaveUser(User user){
        editor = sharedPreferences.edit();
        editor.putString(KEY_USER, new Gson().toJson(user));
        editor.apply();
    }

    public User getUser(){
        if(sharedPreferences.getString(KEY_USER,"").equals("")){
            return null;
        }
        return new Gson().fromJson(sharedPreferences.getString(KEY_USER,""),User.class);
    }

    public void saveLastLat(double value){
        editor = sharedPreferences.edit();
        editor.putLong(KEY_LAST_LAT,Double.doubleToRawLongBits(value));
        editor.apply();
    }

    public double getLastLat(){
        return Double.longBitsToDouble(sharedPreferences.getLong(KEY_LAST_LAT,0));
    }

    public void saveLastLng(double value){
        editor = sharedPreferences.edit();
        editor.putLong(KEY_LAST_LNG,Double.doubleToRawLongBits(value));
        editor.apply();
    }

    public double getLastLng(){
        return Double.longBitsToDouble(sharedPreferences.getLong(KEY_LAST_LNG,0));
    }

    public boolean isUserLogin(){
        return sharedPreferences.getBoolean(KEY_IS_LOGIN, false);
    }

    public boolean isUserSubscribed(){
        return sharedPreferences.getBoolean(KEY_IS_SUBSCRIBED, false);
    }
}
