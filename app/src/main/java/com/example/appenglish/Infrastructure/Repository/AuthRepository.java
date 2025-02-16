package com.example.appenglish.Infrastructure.Repository;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appenglish.Application.Local.SharedPreferencesHelper;
import com.example.appenglish.Application.Remote.AuthApi;
import com.example.appenglish.Domain.Model.LoginRequest;
import com.example.appenglish.Infrastructure.IRepository.IAuthRepository;
import com.example.appenglish.MainActivity;
import com.example.appenglish.Presentation.Ui.Auth.AuthActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.gson.JsonObject;

import javax.inject.Inject;
import javax.inject.Singleton;


import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class AuthRepository implements IAuthRepository {
    private AuthApi authApi;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private final GoogleSignInClient googleSignInClient;
    private Context context;

    @Inject
    public AuthRepository(AuthApi authApi, SharedPreferencesHelper sharedPreferencesHelper, @ApplicationContext Context context) {
        this.authApi = authApi;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
        this.context = context;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("954871855690-ebeilhq9a689iia9plkl3d9o9sfng7ci.apps.googleusercontent.com") // Lấy từ Google Cloud Console
                .requestEmail()
                .build();
        this.googleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    @Override
    public LiveData<LoginRequest.LoginResponse> login(LoginRequest loginRequest) {
        MutableLiveData<LoginRequest.LoginResponse> loginResponse = new MutableLiveData<>();
        authApi.login(loginRequest).enqueue(new Callback<LoginRequest.LoginResponse>() {
            @Override
            public void onResponse(Call<LoginRequest.LoginResponse> call, Response<LoginRequest.LoginResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().getReturnCode().equals("1")) {
                        // Lưu token vào SharedPreferences
                        sharedPreferencesHelper.saveToken(response.body().getToken());
                        loginResponse.setValue(response.body());


                    } else if (response.body().getReturnCode().equals("-1")) {
                        // Xử lý khi đăng nhập thất bại
                        loginResponse.setValue(null);
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginRequest.LoginResponse> call, Throwable t) {
                loginResponse.setValue(null);
            }
        });
        return loginResponse;
    }


    //google

    public void loginWithGoogle(GoogleSignInAccount account) {
        String idToken = account.getId();
        String email = account.getEmail();
        String name = account.getDisplayName();
        String pictureUrl = (account.getPhotoUrl() != null) ? account.getPhotoUrl().toString() : null;

        // Gửi lên API backend
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("email", email);
        json.addProperty("googleId", idToken);
        json.addProperty("pictureUrl", pictureUrl);

        authApi.googleLogin(json).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    // Lưu token nếu có
                    if (response.body().get("returnCode").getAsString().equals("1")) {
                        // Lưu token vào SharedPreferences
                        String token = response.body().get("token").getAsString();
                        sharedPreferencesHelper.saveToken(token);

                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa stack cũ
                        context.startActivity(intent);
                    }

                } else {
                    // Xử lý lỗi
                    Toast.makeText(context, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public Intent getGoogleSignInIntent() {
        return googleSignInClient.getSignInIntent();
    }

    public GoogleSignInAccount handleGoogleSignInResult(ActivityResult result) throws ApiException {
        return GoogleSignIn.getSignedInAccountFromIntent(result.getData()).getResult(ApiException.class);
    }
}
