package com.example.appenglish.Domain.ViewModel;

import android.app.Application;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.appenglish.Domain.Model.LoginRequest;

import com.example.appenglish.Infrastructure.IRepository.IAuthRepository;
import com.example.appenglish.Infrastructure.Repository.AuthRepository;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;

import java.io.Closeable;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthViewModel extends AndroidViewModel {
    private AuthRepository authRepository;

    @Inject
    public AuthViewModel(AuthRepository authRepository, Application application) {
        super( application);
        this.authRepository = authRepository;
    }

    public LiveData<LoginRequest.LoginResponse> login(LoginRequest loginRequest) {
        return authRepository.login(loginRequest);
    }

    public Intent getGoogleSignInIntent() {
        return authRepository.getGoogleSignInIntent();
    }

    public GoogleSignInAccount handleGoogleSignInResult(ActivityResult result) throws ApiException {
        return authRepository.handleGoogleSignInResult(result);
    }

    public void loginWithGoogle(GoogleSignInAccount account) {
        authRepository.loginWithGoogle(account);
    }
}
