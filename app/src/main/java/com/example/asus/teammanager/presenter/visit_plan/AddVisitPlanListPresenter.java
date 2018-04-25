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

public class AddVisitPlanListPresenter {
    private GlobalPresenter globalPresenter;

    public AddVisitPlanListPresenter (GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }


    public void AddVisitPlanList(String token, int visit_plan_id, int customer_id, int day, int type, String description){
        Map<String, Object> data = new HashMap<>();

        data.put("visit_plan_id", visit_plan_id);
        data.put("customer_id", customer_id);
        data.put("day", day);
        data.put("type", type);
        data.put("description", description);

        ApiConnection.getInstance().getRoutes().addVisitPlanList("Bearer ".concat(token), data).enqueue(new Callback<Message>() {
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
