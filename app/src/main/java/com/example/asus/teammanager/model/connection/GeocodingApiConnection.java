package com.example.asus.teammanager.model.connection;

import com.example.asus.teammanager.model.route.Routes;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeocodingApiConnection {
    private static final String BASE_URL = "https://maps.googleapis.com/";
    private static Routes routes;

    public static GeocodingApiConnection getInstance(){
        return new GeocodingApiConnection();
    }

    public Routes getRoutes(){
        return routes;
    }

    private GeocodingApiConnection(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        routes = retrofit.create(Routes.class);
    }
}
