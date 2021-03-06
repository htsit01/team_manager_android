package com.example.asus.teammanager.presenter.visit_plan;

import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.model.response.Message;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVisitPlanPresenter {
    private GlobalPresenter globalPresenter;

    public AddVisitPlanPresenter(GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void addVisitPlan(String token, String valid_date){
        Map<String, String> data = new HashMap<>();
        data.put("valid_date", valid_date);

        ApiConnection.getInstance().getRoutes().addVisitPlan("Bearer ".concat(token), data).enqueue(new Callback<Message>() {
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
