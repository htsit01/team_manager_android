package com.example.asus.teammanager.presenter.location_presenter;

import com.example.asus.teammanager.model.api_model.UserLocation;
import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllLocationsPresenter {
    private GlobalPresenter globalPresenter;

    public GetAllLocationsPresenter (GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void getAllLocations(String token){
        ApiConnection.getInstance().getRoutes().getAllLocations("Bearer ".concat(token)).enqueue(new Callback<ArrayList<UserLocation>>() {
            @Override
            public void onResponse(Call<ArrayList<UserLocation>> call, Response<ArrayList<UserLocation>> response) {
                if(response.code()==200){
                    globalPresenter.onSuccess(response.body());
                }
                else {
                    try {
                        globalPresenter.onError(response.code(), response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserLocation>> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
