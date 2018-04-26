package com.example.asus.teammanager.presenter.customer_presenter;

import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.model.response.Message;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckinPresenter {
    private GlobalPresenter globalPresenter;

    public CheckinPresenter (GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void doCheckin(String token, int customer_id, double lat, double lng, String address, String date_time, int plan_id){

        Map<String, Object> data = new HashMap<>();
        data.put("customer_id", customer_id);
        data.put("lat", lat);
        data.put("lng", lng);
        data.put("address", address);
        data.put("date_time", date_time);
        data.put("plan_id", plan_id);

        ApiConnection.getInstance().getRoutes().doCheckin("Bearer ".concat(token), data).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
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
            public void onFailure(Call<Message> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
