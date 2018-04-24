package com.example.asus.teammanager.presenter.date_presenter;

import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatePresenter {
    public GlobalPresenter globalPresenter;

    public DatePresenter (GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void getFirstMonday(int year, int month){
        ApiConnection.getInstance().getRoutes().getFirstMonday(year, month).enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if(response.code()==200){
                    globalPresenter.onSuccess(response.body());
                }
                else{
                    try {
                        globalPresenter.onError(response.code(), response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
