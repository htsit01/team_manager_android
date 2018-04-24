package com.example.asus.teammanager.model.route;

import com.example.asus.teammanager.model.api_model.CustomerType;
import com.example.asus.teammanager.model.api_model.User;
import com.example.asus.teammanager.model.api_model.UserLocation;
import com.example.asus.teammanager.model.api_model.VisitPlanList;
import com.example.asus.teammanager.model.api_model.VisitPlanWithCount;
import com.example.asus.teammanager.model.response.LocationHistoryResponse;
import com.example.asus.teammanager.model.response.LoginResponse;
import com.example.asus.teammanager.model.response.Message;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Routes {

    /*
    START OF GET ROUTE
     */
    @Headers("Accept:application/json")
    @GET("api/fetchcustomertype")
    Call<ArrayList<CustomerType>> getCustomerType();

    @Headers("Accept:application/json")
    @GET("api/user")
    Call<User> getUser(@Header("Authorization") String header);

    @Headers("Accept:application/json")
    @GET("api/all-locations")
    Call<ArrayList<UserLocation>> getAllLocations(@Header("Authorization") String header);

    @Headers("Accept:application/json")
    @GET("api/location-history/{id}")
    Call<LocationHistoryResponse> getLocationHistory(@Header("Authorization") String header, @Path("id") int id, @Query("day") Integer day);

    @Headers("Accept:application/json")
    @GET("api/visitplan")
    Call<ArrayList<VisitPlanWithCount>> getMyVisitPlan(@Header("Authorization") String header, @Query("valid_date") String valid_date);

    @Headers("Accept:application/json")
    @GET("api/firstmonday")
    Call<ArrayList<String>> getFirstMonday(@Query("year") int year, @Query("month") int month);

    @Headers("Accept:application/json")
    @GET("api/visitplan/{id}/plan-list")
    Call<ArrayList<VisitPlanList>> getPlanList(@Header("Authorization") String header, @Path("id") int visit_plan_id,@Query("day") int day);

//    @Headers("Accept:application/json")
//    @GET("api/fetchsalesperson")
//
//
//    @Headers("Accept:application/json")
//    @GET("api/fetchcustomerarea")
//
//
//
//    @Headers("Accept:application/json")
//    @GET("api/fetchcustomer")


    /*
    END OF GET ROUTE
     */


    /*
    START OF POST ROUTE
     */
    @Headers("Accept:application/json")
    @POST("api/login")
    Call<LoginResponse> postLogin(@Body Map<String, String> data);

    @Headers("Accept:application/json")
    @POST("api/add-plan")
    Call<Message> addVisitPlan(@Header("Authorization") String header,  @Body Map<String, String> data);

    @Headers("Accept:application/json")
    @POST("api/visitplan/delete")
    Call<Message> deleteVisitPlan(@Header("Authorization") String header, @Body Map<String, Integer> data);
}
