package com.example.asus.teammanager.presenter.location_presenter;

import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.model.response.LocationHistoryResponse;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetLocationHistoryPresenter {
    private GlobalPresenter globalPresenter;

    public GetLocationHistoryPresenter(GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void getLocationHistory(String token, int id, int day){
        ApiConnection.getInstance().getRoutes().getLocationHistory("Bearer ".concat(token), id, day).enqueue(new Callback<LocationHistoryResponse>() {
            @Override
            public void onResponse(Call<LocationHistoryResponse> call, Response<LocationHistoryResponse> response) {
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
            public void onFailure(Call<LocationHistoryResponse> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
