package com.example.appenglish.Infrastructure.IRepository;

import androidx.lifecycle.LiveData;

import com.example.appenglish.Domain.Model.LoginRequest;

public interface IAuthRepository {

    LiveData<LoginRequest.LoginResponse> login(LoginRequest loginRequest);

}
