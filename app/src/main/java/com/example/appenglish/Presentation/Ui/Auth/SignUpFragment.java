package com.example.appenglish.Presentation.Ui.Auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.appenglish.R;
import com.google.android.material.textview.MaterialTextView;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class SignUpFragment extends BaseFragment {


    private NavController navController;
    private MaterialTextView loginBtn;
    private LinearLayout siggIn;

    public SignUpFragment() {
        super(R.layout.fragment_sign_up);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        loginBtn = view.findViewById(R.id.RouteToSignIn);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        loginBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_RegisterFragment_to_LoginFramgnet);
        });

        siggIn = view.findViewById(R.id.SignIn);
        siggIn.setOnClickListener(v -> {
            showLoading();
            navController.navigate(R.id.action_RegisterFragment_to_LoginFramgnet);
            hideLoadingWithDelay(2000);
        });
        return view;
    }
}