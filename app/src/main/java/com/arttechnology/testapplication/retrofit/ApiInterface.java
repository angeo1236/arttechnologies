package com.arttechnology.testapplication.retrofit;


import com.arttechnology.testapplication.data.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("users")
    Call<List<UserModel>> get_homepage_data();
}
