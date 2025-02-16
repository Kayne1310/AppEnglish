package com.example.appenglish.Presentation.Ui.Auth;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appenglish.R;



public abstract  class BaseFragment  extends Fragment {
    private LottieAnimationView lottieLoading;
    private Handler handler = new Handler();

    public BaseFragment(@LayoutRes int layoutId) {
        super(layoutId);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lottieLoading = view.findViewById(R.id.loading); // Lấy reference từ layout chung
    }

    public void showLoading() {
        if (lottieLoading != null) {
            lottieLoading.setVisibility(View.VISIBLE);
            lottieLoading.playAnimation();
        }
    }

    public void hideLoadingWithDelay(long delay) {
        handler.postDelayed(() -> {
            if (lottieLoading != null) {
                lottieLoading.cancelAnimation();
                lottieLoading.setVisibility(View.GONE);
            }
        }, delay);
    }
}
