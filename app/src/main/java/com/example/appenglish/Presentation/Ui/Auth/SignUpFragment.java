package com.example.appenglish.Presentation.Ui.Auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import com.example.appenglish.Domain.Model.UserLoginRequest;
import com.example.appenglish.Domain.ViewModel.AuthViewModel;
import com.example.appenglish.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class SignUpFragment extends BaseFragment {

    private AuthViewModel authViewModel;
    private NavController navController;
    private MaterialTextView loginBtn;
    private LinearLayout siggIn, btnGoogle;
    private TextInputEditText txtUserName, txtEmail, txtPassword;

    public SignUpFragment() {
        super(R.layout.fragment_sign_up);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtEmail = view.findViewById(R.id.txtEmailSingup);
        txtUserName = view.findViewById(R.id.txtUserName);
        txtPassword = view.findViewById(R.id.txtPasswordSignUp);
        loginBtn = view.findViewById(R.id.RouteToSignIn);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        btnGoogle = view.findViewById(R.id.btnGoogleSignUp);

        //khoi tao viewmodel provider
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);


        //route ve register to login
        loginBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_RegisterFragment_to_LoginFramgnet);
        });

        //signIN
        siggIn = view.findViewById(R.id.SignIn);
        siggIn.setOnClickListener(v -> {
            if (!validateInput()) return;  // Kiểm tra dữ liệu đầu vào


            UserLoginRequest userLoginRequest = getUserInput();
            authViewModel.signup(userLoginRequest).observe(getViewLifecycleOwner(), uiState -> {
                if (uiState.isLoading) {
                    showLoading();
                    siggIn.setEnabled(false);
                } else {

                    siggIn.setEnabled(true);

                    new Handler(Looper.getMainLooper()).postDelayed(() -> {

                        if (uiState.data != null) {
                            navigateToLogin();
                            Toast.makeText(getActivity(), "Register Successful!", Toast.LENGTH_LONG).show();
                            hideLoadingWithDelay(1);
                        } else if (uiState.error != null) {

                            Toast.makeText(getActivity(), "Register Failed: " + uiState.error, Toast.LENGTH_SHORT).show();
                            hideLoadingWithDelay(1);
                        }
                    }, 2000);

                }
            });

        });


        //GOOGLE SIGNUP
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInLauncher.launch(authViewModel.getGoogleSignInIntent());
            }
        });

    }

    //  Hàm lấy dữ liệu từ input
    private UserLoginRequest getUserInput() {
        return new UserLoginRequest(
                txtUserName.getText().toString(),
                txtEmail.getText().toString(),
                txtPassword.getText().toString()
        );
    }

    //  Hàm validate input
    private boolean validateInput() {
        if (txtUserName.getText().toString().isEmpty() ||
                txtEmail.getText().toString().isEmpty() ||
                txtPassword.getText().toString().isEmpty()) {

            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //  Hàm điều hướng
    private void navigateToLogin() {
        navController.navigate(R.id.action_RegisterFragment_to_LoginFramgnet);
    }

    //google open activity

    private final ActivityResultLauncher<Intent> googleSignInLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    try {
                        GoogleSignInAccount account = authViewModel.handleGoogleSignInResult(result);
                        authViewModel.signupGoogle(account).observe(getViewLifecycleOwner(), returnDataUiState -> {

                            if (returnDataUiState.isLoading) {
                                showLoading();
                                siggIn.setEnabled(false);
                            } else {

                                new Handler(Looper.getMainLooper()).postDelayed(() -> {

                                    if (returnDataUiState.data != null) {
                                        navigateToLogin();
                                        Toast.makeText(getActivity(), "Register Successful!", Toast.LENGTH_LONG).show();
                                        hideLoadingWithDelay(1);
                                    } else if (returnDataUiState.error != null) {

                                        Toast.makeText(getActivity(), "Register Failed: " + returnDataUiState.error, Toast.LENGTH_SHORT).show();
                                        hideLoadingWithDelay(1);
                                    }
                                }, 1000);
                            }
                        });
                        // Giữ animation loading

                        hideLoadingWithDelay(1000);

                    } catch (ApiException e) {
                        e.printStackTrace();
                        hideLoadingWithDelay(1000);

                    }
                }
            });


}