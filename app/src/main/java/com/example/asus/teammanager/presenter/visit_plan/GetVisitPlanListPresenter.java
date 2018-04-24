package com.example.asus.teammanager.presenter.visit_plan;

import com.example.asus.teammanager.model.api_model.VisitPlanList;
import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetVisitPlanListPresenter {
    private GlobalPresenter globalPresenter;

    public GetVisitPlanListPresenter(GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void getPlanList(String token, int visit_plan_id, int day){
        ApiConnection.getInstance().getRoutes().getPlanList("Bearer ".concat(token), visit_plan_id, day).enqueue(new Callback<ArrayList<VisitPlanList>>() {
            @Override
            public void onResponse(Call<ArrayList<VisitPlanList>> call, Response<ArrayList<VisitPlanList>> response) {
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
            public void onFailure(Call<ArrayList<VisitPlanList>> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
