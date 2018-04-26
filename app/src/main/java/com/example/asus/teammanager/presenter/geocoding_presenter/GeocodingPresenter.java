package com.example.asus.teammanager.presenter.geocoding_presenter;

import com.example.asus.teammanager.model.connection.GeocodingApiConnection;
import com.example.asus.teammanager.model.response.GeocodingApiResult;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeocodingPresenter {
    private GlobalPresenter globalPresenter;

    public GeocodingPresenter (GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void getGeocodingLocation(String latlng, String key)
    {
        GeocodingApiConnection.getInstance().getRoutes().getLocationResult(latlng, key).enqueue(new Callback<GeocodingApiResult>() {
            @Override
            public void onResponse(Call<GeocodingApiResult> call, Response<GeocodingApiResult> response) {
                if(response.body().getStatus().equals("OK")){
                    globalPresenter.onSuccess(response.body());
                }
                else{
                    globalPresenter.onError(response.code(),response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<GeocodingApiResult> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
    public void getGeocodingLatLng(String address, String key)
    {
        GeocodingApiConnection.getInstance().getRoutes().getLatngResult(address,key).enqueue(new Callback<GeocodingApiResult>() {
            @Override
            public void onResponse(Call<GeocodingApiResult> call, Response<GeocodingApiResult> response) {
                if(response.body().getStatus().equals("OK")) {
                    globalPresenter.onSuccess(response.body());
                }
                else {
                    globalPresenter.onError(response.code(),response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<GeocodingApiResult> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
