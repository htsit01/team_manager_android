package com.example.asus.teammanager.model.connection;

import com.example.asus.teammanager.model.route.Routes;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConnection {
    private static final String BASE_URL = "http://192.168.10.18:8000";
    private static Routes routes;

    public static ApiConnection getInstance(){
        return new ApiConnection();
    }

    public Routes getRoutes(){
        return routes;
    }

    private ApiConnection(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL.concat("/"))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        routes = retrofit.create(Routes.class);
    }
    public String getBaseurl() {
        return BASE_URL;
    }
}
