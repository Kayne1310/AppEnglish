package com.example.appenglish.Application.Remote;

import com.example.appenglish.Domain.Model.LoginRequest;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {


    @POST("api/Account")
    Call<LoginRequest.LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/Account/google-login")
    Call<JsonObject>  googleLogin(@Body JsonObject jsonObject);
}
