package com.example.appenglish.DI;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.appenglish.Application.Local.SharedPreferencesHelper;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    // This class is responsible for intercepting the request and adding the token to the header

    private final SharedPreferencesHelper sharedPreferencesHelper;

    @Inject
    public AuthInterceptor(SharedPreferencesHelper sharedPreferencesHelper) {
        this.sharedPreferencesHelper = sharedPreferencesHelper;
    }


    @Override
    public Response intercept( Chain chain) throws IOException {
        String token = sharedPreferencesHelper.getToken();
        Request.Builder requestBuilder = chain.request().newBuilder();

        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + token);
        }

        return chain.proceed(requestBuilder.build());
    }
}
