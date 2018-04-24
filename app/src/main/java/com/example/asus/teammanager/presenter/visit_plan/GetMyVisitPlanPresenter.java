package com.example.asus.teammanager.presenter.visit_plan;

import com.example.asus.teammanager.model.api_model.VisitPlanWithCount;
import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMyVisitPlanPresenter {
    private GlobalPresenter globalPresenter;

    public GetMyVisitPlanPresenter (GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void getMyVisitPlan(String token, String valid_date){
        ApiConnection.getInstance().getRoutes().getMyVisitPlan("Bearer ".concat(token), valid_date).enqueue(new Callback<ArrayList<VisitPlanWithCount>>() {
            @Override
            public void onResponse(Call<ArrayList<VisitPlanWithCount>> call, Response<ArrayList<VisitPlanWithCount>> response) {
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
            public void onFailure(Call<ArrayList<VisitPlanWithCount>> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
