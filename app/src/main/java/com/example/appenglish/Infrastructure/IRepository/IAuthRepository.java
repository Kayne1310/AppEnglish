package com.example.appenglish.Infrastructure.IRepository;

import androidx.lifecycle.LiveData;

import com.example.appenglish.Domain.Model.LoginRequest;
import com.example.appenglish.Domain.Model.ReturnData;
import com.example.appenglish.Domain.Model.UserLoginRequest;

public interface IAuthRepository {

    LiveData<LoginRequest.LoginResponse> login(LoginRequest loginRequest);

    LiveData<ReturnData> singup(UserLoginRequest userLoginRequest);

}
