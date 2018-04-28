package com.example.asus.teammanager.presenter.auth_presenter;

import com.example.asus.teammanager.model.connection.ApiConnection;
import com.example.asus.teammanager.model.response.Message;
import com.example.asus.teammanager.presenter.GlobalPresenter;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutPresenter {

    public GlobalPresenter globalPresenter;

    public LogoutPresenter (GlobalPresenter globalPresenter){
        this.globalPresenter = globalPresenter;
    }

    public void doLogout(String token){
        ApiConnection.getInstance().getRoutes().postLogout("Bearer ".concat(token))
                .enqueue(new Callback<Message>() {
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
