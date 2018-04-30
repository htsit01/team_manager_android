package com.example.asus.teammanager.presenter.visit_plan;

import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.model.response.SalesmanPlanListResponse;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetSalesmanVisitPlanListPresenter {
    private GlobalPresenter globalPresenter;

    public GetSalesmanVisitPlanListPresenter (GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void getSalesmanVisitPlanList(String token, int id, int day){
        ApiConnection.getInstance().getRoutes().getSalesmanVisitPlanList("Bearer ".concat(token), id, day).enqueue(new Callback<SalesmanPlanListResponse>() {
            @Override
            public void onResponse(Call<SalesmanPlanListResponse> call, Response<SalesmanPlanListResponse> response) {
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
            public void onFailure(Call<SalesmanPlanListResponse> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
