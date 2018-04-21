package com.example.asus.teammanager.presenter;

public interface GlobalPresenter {
    void onSuccess(Object object);
    void onError(int code, String message);
    void onFail(String message);
}
