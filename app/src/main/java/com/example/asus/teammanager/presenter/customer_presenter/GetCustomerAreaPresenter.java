package com.example.asus.teammanager.presenter.customer_presenter;

import com.example.asus.teammanager.model.api_model.CustomerArea;
import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCustomerAreaPresenter {
    private GlobalPresenter globalPresenter;

    public GetCustomerAreaPresenter (GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void getCustomerArea(String token){
        ApiConnection.getInstance().getRoutes().getCustomerArea("Bearer ".concat(token)).enqueue(new Callback<ArrayList<CustomerArea>>() {
            @Override
            public void onResponse(Call<ArrayList<CustomerArea>> call, Response<ArrayList<CustomerArea>> response) {
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
            public void onFailure(Call<ArrayList<CustomerArea>> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
