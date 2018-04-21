package com.example.asus.teammanager.presenter.user_presenter;

import com.example.asus.teammanager.model.api_model.User;
import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserPresenter {
    private GlobalPresenter globalPresenter;

    public GetUserPresenter(GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void getUser(String token){
        ApiConnection.getInstance().getRoutes().getUser("Bearer ".concat(token)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
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
            public void onFailure(Call<User> call, Throwable t) {
                globalPresenter.onFail(t.getMessage());
            }
        });
    }
}
