package com.example.asus.teammanager.presenter.followup_presenter;

import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.model.response.Message;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowUpCheckoutPresenter {
    private GlobalPresenter globalPresenter;

    public FollowUpCheckoutPresenter(GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void doCheckout(String token, int followup_id, double lat, double lng, String address, String finish_time, int type){
        Map<String, Object> data = new HashMap<>();
        data.put("followup_id", followup_id);
        data.put("lat", lat);
        data.put("lng", lng);
        data.put("address", address);
        data.put("finish_time", finish_time);
        data.put("type", type);

        ApiConnection.getInstance().getRoutes().doFollowUpCheckout("Bearer ".concat(token),data).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.code()==200){
                    globalPresenter.onSuccess(response.body());
                }
                else{
                    globalPresenter.onError(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
