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

public class DeleteMyVisitPlanPresenter {
    private GlobalPresenter globalPresenter;

    public DeleteMyVisitPlanPresenter(GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void deleteMyVisitPlan(String token, int id){
        Map<String, Integer> data = new HashMap<>();
        data.put("id", id);

        ApiConnection.getInstance().getRoutes().deleteVisitPlan("Bearer ".concat(token), data).enqueue(new Callback<Message>() {
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
