package com.example.appenglish.Presentation.Ui.Main.FlashCard;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appenglish.R;

public class FlashCardFragment extends Fragment {

    private FlashCardViewModel mViewModel;

    public static FlashCardFragment newInstance() {
        return new FlashCardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flash_card, container, false);
    }



}