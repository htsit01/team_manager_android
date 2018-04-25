package com.example.asus.teammanager.presenter.customer_presenter;

import com.example.asus.teammanager.model.api_model.Customer;
import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCustomerPresenter {
    private GlobalPresenter globalPresenter;

    public GetCustomerPresenter (GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void getCustomer(String token){
        ApiConnection.getInstance().getRoutes().getCustomer("Bearer ".concat(token)).enqueue(new Callback<ArrayList<Customer>>() {
            @Override
            public void onResponse(Call<ArrayList<Customer>> call, Response<ArrayList<Customer>> response) {
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
            public void onFailure(Call<ArrayList<Customer>> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
