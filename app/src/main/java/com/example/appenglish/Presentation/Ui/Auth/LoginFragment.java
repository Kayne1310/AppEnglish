package com.example.appenglish.Presentation.Ui.Auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appenglish.Domain.Model.LoginRequest;
import com.example.appenglish.Domain.ViewModel.AuthViewModel;
import com.example.appenglish.Presentation.Ui.Main.MainActivity;
import com.example.appenglish.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends BaseFragment {

    private AuthViewModel authViewModel;
    private NavController navController;
    private MaterialTextView registerBtn, forgotPasswordBtn;
    private TextInputEditText email, password;
    private LinearLayout loginBtn,btnFacebook,btnGoogle;
    private LottieAnimationView lottieAnimationView;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        registerBtn = view.findViewById(R.id.RouteToSignUp);
        forgotPasswordBtn = view.findViewById(R.id.RouteToResetPassword);
        email = view.findViewById(R.id.txtEmail);
        password = view.findViewById(R.id.txtPassword);
//        lottieAnimationView = view.findViewById(R.id.loading);

        loginBtn = view.findViewById(R.id.btnLogin);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        //login btn
        loginBtn.setOnClickListener(v -> {
            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();
            if (emailText.isEmpty() || passwordText.isEmpty()) {
                email.setError("Email is required");
                password.setError("Password is required");
                return;
            }
            showLoading();
                authViewModel.login(new LoginRequest(emailText, passwordText)).observe(getViewLifecycleOwner(), loginResponse -> {


                new Handler(Looper.getMainLooper()).postDelayed(()->{
                    if (loginResponse != null) {
                        //save token
                        Toast.makeText(getActivity(), "Login Successuly !", Toast.LENGTH_SHORT).show();
                        hideLoadingWithDelay(1);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        //navigate to home
                    } else {
                        hideLoadingWithDelay(1);
                        //show error\
                        Toast.makeText(getActivity(), "Login Failed !", Toast.LENGTH_SHORT).show();
                    }

                },2000);
            });
            //call login api
        });

        //google sign in
        btnGoogle = view.findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(v -> {
            showLoading();


            googleSignInLauncher.launch(authViewModel.getGoogleSignInIntent());
        });

        registerBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_LoginFramgnet_to_RegisterFragment);
        });

        forgotPasswordBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_LoginFramgnet_to_ResetPasswordFragment);
        });


        //google sign in
    }
        private final ActivityResultLauncher<Intent> googleSignInLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            GoogleSignInAccount account = authViewModel.handleGoogleSignInResult(result);
                            authViewModel.loginWithGoogle(account);
                           // Giữ animation loading



                            hideLoadingWithDelay(1000);

                        } catch (ApiException e) {
                            e.printStackTrace();
                            hideLoadingWithDelay(1000);

                        }
                    }
                });
}