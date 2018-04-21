package com.example.asus.teammanager.presenter.fetch_presenter;

import com.example.asus.teammanager.model.api_model.CustomerType;
import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchPresenter {
    private GlobalPresenter globalPresenter;

    public FetchPresenter (GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void getFetchPresenter(){
        ApiConnection.getInstance().getRoutes().getCustomerType().enqueue(new Callback<ArrayList<CustomerType>>() {
            @Override
            public void onResponse(Call<ArrayList<CustomerType>> call, Response<ArrayList<CustomerType>> response) {
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
            public void onFailure(Call<ArrayList<CustomerType>> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
