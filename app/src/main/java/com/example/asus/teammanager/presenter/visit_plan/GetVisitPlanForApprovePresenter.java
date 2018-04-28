package com.example.asus.teammanager.presenter.visit_plan;

import com.example.asus.teammanager.model.api_model.VisitPlanForApprove;
import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetVisitPlanForApprovePresenter {
    private GlobalPresenter globalPresenter;

    public GetVisitPlanForApprovePresenter(GlobalPresenter globalPresenter){
        this.globalPresenter  = globalPresenter;
    }

    public void getVisitPlanForApprove(String token, int month, int year){
        ApiConnection.getInstance().getRoutes().getVisitPlanForApprove("Bearer ".concat(token),month, year).enqueue(new Callback<ArrayList<VisitPlanForApprove>>() {
            @Override
            public void onResponse(Call<ArrayList<VisitPlanForApprove>> call, Response<ArrayList<VisitPlanForApprove>> response) {
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
            public void onFailure(Call<ArrayList<VisitPlanForApprove>> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
