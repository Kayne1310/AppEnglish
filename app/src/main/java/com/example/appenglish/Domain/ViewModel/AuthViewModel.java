package com.example.appenglish.Domain.ViewModel;

import android.app.Application;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appenglish.Domain.Model.LoginRequest;

import com.example.appenglish.Domain.Model.ReturnData;
import com.example.appenglish.Domain.Model.UiState;
import com.example.appenglish.Domain.Model.UserLoginRequest;
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
    private final MediatorLiveData<UiState<ReturnData>> uiState = new MediatorLiveData<>();

    @Inject
    public AuthViewModel(AuthRepository authRepository, Application application) {
        super(application);
        this.authRepository = authRepository;
    }



    public LiveData<LoginRequest.LoginResponse> login(LoginRequest loginRequest) {
        return authRepository.login(loginRequest);
    }

    //sinup
    public LiveData<UiState<ReturnData>> signup(UserLoginRequest userLoginRequest) {
        uiState.setValue(UiState.loading());

        LiveData<ReturnData> result = authRepository.singup(userLoginRequest);

        uiState.addSource(result, data -> {
            if (data != null && "1".equals(data.getReturnCode())) {
                uiState.setValue(UiState.success(data));
            } else {
                String errorMessage = (data != null) ? data.getReturnMessage() : "Unknown error";
                uiState.setValue(UiState.error(errorMessage));  //
            }
        });

        return uiState;
    }

    //singupwithGoogle
    public LiveData<UiState<ReturnData>> signupGoogle(GoogleSignInAccount googleSignInAccount){
              uiState.setValue(UiState.loading());
        LiveData<ReturnData> result = authRepository.signUpWithGoogle(googleSignInAccount);
          uiState.addSource(result,data->{
              if(data!=null && data.getReturnCode().equals("1") ){
                  uiState.setValue(UiState.success(data));
              }
              else {
                  String errMess=(data!=null) ?data.getReturnMessage():"Error unkow" ;
                  uiState.setValue(UiState.error(errMess));
              }
          });

        return  uiState;
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
