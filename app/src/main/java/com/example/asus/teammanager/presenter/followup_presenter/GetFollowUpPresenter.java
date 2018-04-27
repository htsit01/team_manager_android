package com.example.asus.teammanager.presenter.followup_presenter;

import com.example.asus.teammanager.model.api_model.FollowUp;
import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFollowUpPresenter {
    private GlobalPresenter globalPresenter;

    public GetFollowUpPresenter (GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void getFollowUp(String token, String status, String date_time){
        ApiConnection.getInstance().getRoutes().getFollowUp("Bearer ".concat(token), status, date_time).enqueue(new Callback<ArrayList<FollowUp>>() {
            @Override
            public void onResponse(Call<ArrayList<FollowUp>> call, Response<ArrayList<FollowUp>> response) {
                if(response.code() == 200){
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
            public void onFailure(Call<ArrayList<FollowUp>> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
