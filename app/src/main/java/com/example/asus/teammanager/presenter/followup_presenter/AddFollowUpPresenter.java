package com.example.asus.teammanager.presenter.followup_presenter;

import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.model.response.Message;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFollowUpPresenter {
    private GlobalPresenter globalPresenter;

    public AddFollowUpPresenter (GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void endUserFollowUp(String token, String name, String address, String date_time, String description){
        Map<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("address", address);
        data.put("date_time",date_time);
        data.put("description", description);

        ApiConnection.getInstance().getRoutes().addFollowUp("Bearer ".concat(token), data).enqueue(new Callback<Message>() {
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

    public void customerFollowUp(String token, int customer_id, String date_time, String description){
        Map<String, Object> data = new HashMap<>();
        data.put("customer_id", customer_id);
        data.put("date_time", date_time);
        data.put("description", description);

        ApiConnection.getInstance().getRoutes().addFollowUpCustomer("Bearer ".concat(token),data).enqueue(new Callback<Message>() {
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
