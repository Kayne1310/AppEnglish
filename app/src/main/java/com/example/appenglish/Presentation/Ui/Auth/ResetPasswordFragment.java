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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordFragment extends Fragment {

    private NavController navController;
    private LinearLayout singIn;
    private MaterialTextView loginBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);

        singIn = view.findViewById(R.id.btn_reset_password);
        loginBtn = view.findViewById(R.id.RouteToSignIn);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        singIn.setOnClickListener(v -> {
            navController.navigate(R.id.action_ResetPasswordFragment_to_LoginFramgnet);
        });
        loginBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_ResetPasswordFragment_to_LoginFramgnet);
        });

        return view;


    }
}